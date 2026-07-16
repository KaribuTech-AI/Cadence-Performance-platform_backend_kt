-- ============================================================================
-- CADENCE PERFORMANCE MANAGEMENT PLATFORM
-- Production-ready PostgreSQL schema
-- Version: 2.0
-- ============================================================================
SET search_path TO cadence, public;
BEGIN;

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

CREATE SCHEMA IF NOT EXISTS cadence;
SET search_path TO cadence, public;

-- ============================================================================
-- ENUMS
-- ============================================================================

CREATE TYPE record_status AS ENUM ('ACTIVE','INACTIVE','ARCHIVED');
CREATE TYPE cycle_status AS ENUM ('DRAFT','SCHEDULED','OPEN','CALIBRATION','CLOSED','CANCELLED');
CREATE TYPE workflow_status AS ENUM ('PENDING','IN_PROGRESS','COMPLETED','REJECTED','CANCELLED','OVERDUE');
CREATE TYPE review_type AS ENUM ('SELF','MANAGER','PEER','DIRECT_REPORT','EXTERNAL','CALIBRATION');
CREATE TYPE goal_status AS ENUM ('DRAFT','ACTIVE','AT_RISK','OFF_TRACK','COMPLETED','CANCELLED');
CREATE TYPE access_scope AS ENUM ('SELF','TEAM','DEPARTMENT','DIVISION','ORGANISATION');
CREATE TYPE notification_channel AS ENUM ('IN_APP','EMAIL','SMS','PUSH');
CREATE TYPE evidence_type AS ENUM ('DOCUMENT','LINK','COMMENT','METRIC','IMAGE','OTHER');
CREATE TYPE audit_event_type AS ENUM ('CREATE','UPDATE','DELETE','LOGIN','LOGOUT','SUBMIT','APPROVE','REJECT','EXPORT','IMPORT','ASSIGN','SYSTEM');

-- ============================================================================
-- COMMON FUNCTIONS
-- ============================================================================

CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = CURRENT_TIMESTAMP;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ============================================================================
-- ORGANISATION STRUCTURE
-- ============================================================================

CREATE TABLE organisations (
  organisation_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(200) NOT NULL,
  legal_name VARCHAR(250),
  registration_number VARCHAR(100),
  timezone VARCHAR(80) NOT NULL DEFAULT 'Africa/Johannesburg',
  default_currency CHAR(3) NOT NULL DEFAULT 'ZAR',
  status record_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organisational_units (
  unit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  parent_unit_id UUID REFERENCES organisational_units(unit_id),
  unit_type VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  code VARCHAR(60),
  manager_user_id UUID,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (organisation_id, code)
);

CREATE TABLE job_grades (
  job_grade_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  grade_code VARCHAR(50) NOT NULL,
  grade_name VARCHAR(150) NOT NULL,
  rank_order INTEGER,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  UNIQUE (organisation_id, grade_code)
);

CREATE TABLE positions (
  position_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  unit_id UUID REFERENCES organisational_units(unit_id),
  job_grade_id UUID REFERENCES job_grades(job_grade_id),
  title VARCHAR(200) NOT NULL,
  position_code VARCHAR(60),
  reports_to_position_id UUID REFERENCES positions(position_id),
  is_managerial BOOLEAN NOT NULL DEFAULT FALSE,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (organisation_id, position_code)
);

-- ============================================================================
-- USERS, EMPLOYEES AND ACCESS CONTROL
-- ============================================================================

CREATE TABLE users (
  user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  email CITEXT NOT NULL,
  employee_number VARCHAR(80),
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  display_name VARCHAR(220),
  phone_number VARCHAR(40),
  password_hash TEXT,
  is_sso_user BOOLEAN NOT NULL DEFAULT FALSE,
  last_login_at TIMESTAMPTZ,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (organisation_id, email),
  UNIQUE (organisation_id, employee_number)
);

ALTER TABLE organisational_units
  ADD CONSTRAINT fk_unit_manager
  FOREIGN KEY (manager_user_id) REFERENCES users(user_id);

CREATE TABLE employee_profiles (
  employee_profile_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL UNIQUE REFERENCES users(user_id) ON DELETE CASCADE,
  position_id UUID REFERENCES positions(position_id),
  manager_user_id UUID REFERENCES users(user_id),
  hire_date DATE,
  termination_date DATE,
  employment_type VARCHAR(50),
  location VARCHAR(150),
  biography TEXT,
  profile_photo_url TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
  role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID REFERENCES organisations(organisation_id),
  role_name VARCHAR(100) NOT NULL,
  description TEXT,
  is_system_role BOOLEAN NOT NULL DEFAULT FALSE,
  UNIQUE (organisation_id, role_name)
);

CREATE TABLE permissions (
  permission_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  permission_key VARCHAR(150) NOT NULL UNIQUE,
  description TEXT
);

CREATE TABLE role_permissions (
  role_id UUID NOT NULL REFERENCES roles(role_id) ON DELETE CASCADE,
  permission_id UUID NOT NULL REFERENCES permissions(permission_id) ON DELETE CASCADE,
  PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user_roles (
  user_role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
  role_id UUID NOT NULL REFERENCES roles(role_id) ON DELETE CASCADE,
  scope access_scope NOT NULL DEFAULT 'SELF',
  scope_unit_id UUID REFERENCES organisational_units(unit_id),
  valid_from DATE,
  valid_to DATE,
  UNIQUE (user_id, role_id, scope, scope_unit_id)
);

-- ============================================================================
-- PERFORMANCE CYCLES AND PHASES
-- ============================================================================

CREATE TABLE review_cycles (
  cycle_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(200) NOT NULL,
  description TEXT,
  cycle_year INTEGER NOT NULL,
  period_label VARCHAR(100),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  status cycle_status NOT NULL DEFAULT 'DRAFT',
  allow_goal_changes BOOLEAN NOT NULL DEFAULT TRUE,
  allow_late_submission BOOLEAN NOT NULL DEFAULT FALSE,
  created_by UUID REFERENCES users(user_id),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (end_date >= start_date)
);

CREATE TABLE cycle_phases (
  phase_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id) ON DELETE CASCADE,
  phase_name VARCHAR(150) NOT NULL,
  phase_code VARCHAR(60) NOT NULL,
  sequence_no INTEGER NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  UNIQUE (cycle_id, phase_code),
  UNIQUE (cycle_id, sequence_no),
  CHECK (end_date >= start_date)
);

CREATE TABLE cycle_participants (
  participant_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id) ON DELETE CASCADE,
  employee_id UUID NOT NULL REFERENCES users(user_id),
  manager_id UUID REFERENCES users(user_id),
  unit_id UUID REFERENCES organisational_units(unit_id),
  status workflow_status NOT NULL DEFAULT 'PENDING',
  joined_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (cycle_id, employee_id)
);

-- ============================================================================
-- GOALS AND OKRS
-- ============================================================================

CREATE TABLE goal_templates (
  goal_template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(200) NOT NULL,
  description TEXT,
  default_weight NUMERIC(5,2),
  status record_status NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE goals (
  goal_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id),
  owner_user_id UUID NOT NULL REFERENCES users(user_id),
  parent_goal_id UUID REFERENCES goals(goal_id),
  goal_template_id UUID REFERENCES goal_templates(goal_template_id),
  title VARCHAR(300) NOT NULL,
  description TEXT,
  alignment_type VARCHAR(50),
  alignment_reference VARCHAR(250),
  weight NUMERIC(5,2) NOT NULL DEFAULT 0,
  progress NUMERIC(5,2) NOT NULL DEFAULT 0,
  status goal_status NOT NULL DEFAULT 'DRAFT',
  start_date DATE,
  due_date DATE,
  approved_by UUID REFERENCES users(user_id),
  approved_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (weight BETWEEN 0 AND 100),
  CHECK (progress BETWEEN 0 AND 100)
);

CREATE TABLE key_results (
  key_result_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  goal_id UUID NOT NULL REFERENCES goals(goal_id) ON DELETE CASCADE,
  title VARCHAR(300) NOT NULL,
  metric_type VARCHAR(50),
  baseline_value NUMERIC(18,4),
  target_value NUMERIC(18,4),
  current_value NUMERIC(18,4),
  unit_of_measure VARCHAR(50),
  progress NUMERIC(5,2) NOT NULL DEFAULT 0,
  due_date DATE,
  status goal_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (progress BETWEEN 0 AND 100)
);

CREATE TABLE goal_updates (
  goal_update_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  goal_id UUID NOT NULL REFERENCES goals(goal_id) ON DELETE CASCADE,
  created_by UUID NOT NULL REFERENCES users(user_id),
  previous_progress NUMERIC(5,2),
  new_progress NUMERIC(5,2),
  update_comment TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- REVIEW TEMPLATES, COMPETENCIES AND RATING SCALES
-- ============================================================================

CREATE TABLE rating_scales (
  rating_scale_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(150) NOT NULL,
  description TEXT,
  minimum_score NUMERIC(5,2) NOT NULL,
  maximum_score NUMERIC(5,2) NOT NULL,
  status record_status NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE rating_scale_values (
  rating_scale_value_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  rating_scale_id UUID NOT NULL REFERENCES rating_scales(rating_scale_id) ON DELETE CASCADE,
  score NUMERIC(5,2) NOT NULL,
  label VARCHAR(120) NOT NULL,
  description TEXT,
  color_code VARCHAR(20),
  UNIQUE (rating_scale_id, score)
);

CREATE TABLE competency_categories (
  category_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(150) NOT NULL,
  description TEXT,
  status record_status NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE competencies (
  competency_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  category_id UUID REFERENCES competency_categories(category_id),
  name VARCHAR(200) NOT NULL,
  description TEXT,
  code VARCHAR(60),
  status record_status NOT NULL DEFAULT 'ACTIVE',
  UNIQUE (organisation_id, code)
);

CREATE TABLE review_templates (
  review_template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(200) NOT NULL,
  description TEXT,
  rating_scale_id UUID NOT NULL REFERENCES rating_scales(rating_scale_id),
  goal_weight NUMERIC(5,2) NOT NULL DEFAULT 50,
  competency_weight NUMERIC(5,2) NOT NULL DEFAULT 50,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (goal_weight + competency_weight = 100)
);

CREATE TABLE review_template_competencies (
  template_competency_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  review_template_id UUID NOT NULL REFERENCES review_templates(review_template_id) ON DELETE CASCADE,
  competency_id UUID NOT NULL REFERENCES competencies(competency_id),
  weight NUMERIC(5,2) NOT NULL,
  sequence_no INTEGER NOT NULL,
  is_mandatory BOOLEAN NOT NULL DEFAULT TRUE,
  UNIQUE (review_template_id, competency_id)
);

CREATE TABLE cycle_review_templates (
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id) ON DELETE CASCADE,
  review_template_id UUID NOT NULL REFERENCES review_templates(review_template_id),
  unit_id UUID REFERENCES organisational_units(unit_id),
  job_grade_id UUID REFERENCES job_grades(job_grade_id),
  PRIMARY KEY (cycle_id, review_template_id, unit_id, job_grade_id)
);

-- ============================================================================
-- REVIEWS AND WORKFLOW
-- ============================================================================

CREATE TABLE reviews (
  review_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id),
  review_template_id UUID NOT NULL REFERENCES review_templates(review_template_id),
  employee_id UUID NOT NULL REFERENCES users(user_id),
  reviewer_id UUID NOT NULL REFERENCES users(user_id),
  review_type review_type NOT NULL,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  goal_score NUMERIC(6,2),
  competency_score NUMERIC(6,2),
  overall_score NUMERIC(6,2),
  overall_comments TEXT,
  started_at TIMESTAMPTZ,
  submitted_at TIMESTAMPTZ,
  approved_at TIMESTAMPTZ,
  due_date DATE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (cycle_id, employee_id, reviewer_id, review_type)
);

CREATE TABLE review_goal_scores (
  review_goal_score_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  review_id UUID NOT NULL REFERENCES reviews(review_id) ON DELETE CASCADE,
  goal_id UUID NOT NULL REFERENCES goals(goal_id),
  rating NUMERIC(5,2),
  weighted_score NUMERIC(6,2),
  comments TEXT,
  UNIQUE (review_id, goal_id)
);

CREATE TABLE review_competency_scores (
  review_competency_score_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  review_id UUID NOT NULL REFERENCES reviews(review_id) ON DELETE CASCADE,
  competency_id UUID NOT NULL REFERENCES competencies(competency_id),
  rating NUMERIC(5,2),
  weighted_score NUMERIC(6,2),
  comments TEXT,
  UNIQUE (review_id, competency_id)
);

CREATE TABLE review_approvals (
  review_approval_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  review_id UUID NOT NULL REFERENCES reviews(review_id) ON DELETE CASCADE,
  approver_id UUID NOT NULL REFERENCES users(user_id),
  sequence_no INTEGER NOT NULL,
  decision workflow_status NOT NULL DEFAULT 'PENDING',
  comments TEXT,
  decided_at TIMESTAMPTZ,
  UNIQUE (review_id, sequence_no)
);

-- ============================================================================
-- 360 FEEDBACK
-- ============================================================================

CREATE TABLE feedback_questionnaires (
  questionnaire_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  name VARCHAR(200) NOT NULL,
  description TEXT,
  status record_status NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE feedback_questions (
  question_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  questionnaire_id UUID NOT NULL REFERENCES feedback_questionnaires(questionnaire_id) ON DELETE CASCADE,
  question_text TEXT NOT NULL,
  response_type VARCHAR(50) NOT NULL,
  competency_id UUID REFERENCES competencies(competency_id),
  sequence_no INTEGER NOT NULL
);

CREATE TABLE feedback_requests (
  feedback_request_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id),
  questionnaire_id UUID REFERENCES feedback_questionnaires(questionnaire_id),
  requested_for_user_id UUID NOT NULL REFERENCES users(user_id),
  requested_from_user_id UUID NOT NULL REFERENCES users(user_id),
  requested_by_user_id UUID NOT NULL REFERENCES users(user_id),
  relationship_type VARCHAR(50),
  status workflow_status NOT NULL DEFAULT 'PENDING',
  due_date DATE,
  is_anonymous BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (cycle_id, requested_for_user_id, requested_from_user_id, questionnaire_id)
);

CREATE TABLE feedback_responses (
  feedback_response_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  feedback_request_id UUID NOT NULL REFERENCES feedback_requests(feedback_request_id) ON DELETE CASCADE,
  question_id UUID REFERENCES feedback_questions(question_id),
  numeric_response NUMERIC(5,2),
  text_response TEXT,
  submitted_at TIMESTAMPTZ
);

-- ============================================================================
-- CHECK-INS
-- ============================================================================

CREATE TABLE checkins (
  checkin_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID REFERENCES review_cycles(cycle_id),
  employee_id UUID NOT NULL REFERENCES users(user_id),
  manager_id UUID NOT NULL REFERENCES users(user_id),
  scheduled_at TIMESTAMPTZ,
  completed_at TIMESTAMPTZ,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  discussion_notes TEXT,
  employee_comments TEXT,
  manager_comments TEXT,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE checkin_action_items (
  action_item_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  checkin_id UUID NOT NULL REFERENCES checkins(checkin_id) ON DELETE CASCADE,
  title VARCHAR(250) NOT NULL,
  owner_user_id UUID REFERENCES users(user_id),
  due_date DATE,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  completed_at TIMESTAMPTZ
);

-- ============================================================================
-- CALIBRATION AND TALENT MANAGEMENT
-- ============================================================================

CREATE TABLE calibration_sessions (
  calibration_session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id),
  name VARCHAR(200) NOT NULL,
  unit_id UUID REFERENCES organisational_units(unit_id),
  session_date TIMESTAMPTZ,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  facilitator_id UUID REFERENCES users(user_id),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE calibration_participants (
  calibration_session_id UUID NOT NULL REFERENCES calibration_sessions(calibration_session_id) ON DELETE CASCADE,
  user_id UUID NOT NULL REFERENCES users(user_id),
  role_in_session VARCHAR(80),
  PRIMARY KEY (calibration_session_id, user_id)
);

CREATE TABLE calibration_results (
  calibration_result_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  calibration_session_id UUID NOT NULL REFERENCES calibration_sessions(calibration_session_id) ON DELETE CASCADE,
  employee_id UUID NOT NULL REFERENCES users(user_id),
  initial_rating NUMERIC(5,2),
  final_rating NUMERIC(5,2),
  performance_band VARCHAR(50),
  potential_band VARCHAR(50),
  nine_box_cell VARCHAR(20),
  rationale TEXT,
  calibrated_by UUID REFERENCES users(user_id),
  calibrated_at TIMESTAMPTZ,
  UNIQUE (calibration_session_id, employee_id)
);

CREATE TABLE distribution_rules (
  distribution_rule_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID NOT NULL REFERENCES review_cycles(cycle_id),
  band_name VARCHAR(100) NOT NULL,
  minimum_percent NUMERIC(5,2),
  target_percent NUMERIC(5,2),
  maximum_percent NUMERIC(5,2),
  sequence_no INTEGER NOT NULL,
  CHECK (minimum_percent BETWEEN 0 AND 100),
  CHECK (target_percent BETWEEN 0 AND 100),
  CHECK (maximum_percent BETWEEN 0 AND 100)
);

-- ============================================================================
-- EVIDENCE, DOCUMENTS AND COMMENTS
-- ============================================================================

CREATE TABLE evidence_items (
  evidence_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  owner_user_id UUID NOT NULL REFERENCES users(user_id),
  evidence_type evidence_type NOT NULL,
  entity_type VARCHAR(80) NOT NULL,
  entity_id UUID NOT NULL,
  title VARCHAR(250),
  description TEXT,
  file_name VARCHAR(255),
  storage_uri TEXT,
  mime_type VARCHAR(120),
  file_size_bytes BIGINT,
  checksum_sha256 VARCHAR(64),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
  comment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  entity_type VARCHAR(80) NOT NULL,
  entity_id UUID NOT NULL,
  author_user_id UUID NOT NULL REFERENCES users(user_id),
  parent_comment_id UUID REFERENCES comments(comment_id),
  comment_text TEXT NOT NULL,
  is_private BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- NOTIFICATIONS AND REMINDERS
-- ============================================================================

CREATE TABLE notification_templates (
  notification_template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  template_key VARCHAR(120) NOT NULL,
  channel notification_channel NOT NULL,
  subject_template TEXT,
  body_template TEXT NOT NULL,
  status record_status NOT NULL DEFAULT 'ACTIVE',
  UNIQUE (organisation_id, template_key, channel)
);

CREATE TABLE notifications (
  notification_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(user_id),
  channel notification_channel NOT NULL DEFAULT 'IN_APP',
  title VARCHAR(250),
  message TEXT NOT NULL,
  entity_type VARCHAR(80),
  entity_id UUID,
  sent_at TIMESTAMPTZ,
  read_at TIMESTAMPTZ,
  delivery_status VARCHAR(50) DEFAULT 'PENDING',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reminder_rules (
  reminder_rule_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  cycle_id UUID REFERENCES review_cycles(cycle_id),
  phase_id UUID REFERENCES cycle_phases(phase_id),
  notification_template_id UUID REFERENCES notification_templates(notification_template_id),
  days_before_due INTEGER,
  repeat_every_days INTEGER,
  maximum_reminders INTEGER DEFAULT 1,
  is_enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- ============================================================================
-- WORKFLOW TASKS
-- ============================================================================

CREATE TABLE workflow_tasks (
  workflow_task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  cycle_id UUID REFERENCES review_cycles(cycle_id),
  task_type VARCHAR(100) NOT NULL,
  entity_type VARCHAR(80),
  entity_id UUID,
  assignee_user_id UUID REFERENCES users(user_id),
  candidate_role_id UUID REFERENCES roles(role_id),
  status workflow_status NOT NULL DEFAULT 'PENDING',
  priority INTEGER NOT NULL DEFAULT 3,
  due_at TIMESTAMPTZ,
  completed_at TIMESTAMPTZ,
  metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workflow_task_history (
  task_history_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  workflow_task_id UUID NOT NULL REFERENCES workflow_tasks(workflow_task_id) ON DELETE CASCADE,
  changed_by UUID REFERENCES users(user_id),
  old_status workflow_status,
  new_status workflow_status,
  comments TEXT,
  changed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- ANALYTICS AND AI INSIGHTS
-- ============================================================================

CREATE TABLE ai_insights (
  ai_insight_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  user_id UUID REFERENCES users(user_id),
  cycle_id UUID REFERENCES review_cycles(cycle_id),
  insight_type VARCHAR(100) NOT NULL,
  summary TEXT NOT NULL,
  recommendation TEXT,
  confidence_score NUMERIC(5,2),
  model_name VARCHAR(150),
  model_version VARCHAR(80),
  source_entity_type VARCHAR(80),
  source_entity_id UUID,
  metadata JSONB DEFAULT '{}'::jsonb,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CHECK (confidence_score IS NULL OR confidence_score BETWEEN 0 AND 100)
);

CREATE TABLE report_exports (
  export_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  organisation_id UUID NOT NULL REFERENCES organisations(organisation_id),
  requested_by UUID NOT NULL REFERENCES users(user_id),
  report_type VARCHAR(100) NOT NULL,
  file_format VARCHAR(20) NOT NULL,
  filters JSONB DEFAULT '{}'::jsonb,
  storage_uri TEXT,
  status workflow_status NOT NULL DEFAULT 'PENDING',
  requested_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completed_at TIMESTAMPTZ
);

-- ============================================================================
-- AUDIT AND SECURITY
-- ============================================================================

CREATE TABLE audit_log (
  audit_id BIGSERIAL PRIMARY KEY,
  organisation_id UUID REFERENCES organisations(organisation_id),
  actor_user_id UUID REFERENCES users(user_id),
  event_type audit_event_type NOT NULL,
  entity_type VARCHAR(100),
  entity_id UUID,
  action_description TEXT,
  old_values JSONB,
  new_values JSONB,
  ip_address INET,
  user_agent TEXT,
  correlation_id UUID,
  event_hash VARCHAR(64),
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_sessions (
  session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
  refresh_token_hash TEXT,
  ip_address INET,
  user_agent TEXT,
  expires_at TIMESTAMPTZ NOT NULL,
  revoked_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- INDEXES
-- ============================================================================

CREATE INDEX idx_users_org_status ON users(organisation_id, status);
CREATE INDEX idx_users_manager ON employee_profiles(manager_user_id);
CREATE INDEX idx_units_parent ON organisational_units(parent_unit_id);
CREATE INDEX idx_cycles_org_status ON review_cycles(organisation_id, status);
CREATE INDEX idx_cycle_participants_cycle_status ON cycle_participants(cycle_id, status);
CREATE INDEX idx_goals_owner_cycle ON goals(owner_user_id, cycle_id);
CREATE INDEX idx_goals_status_due ON goals(status, due_date);
CREATE INDEX idx_reviews_employee_cycle ON reviews(employee_id, cycle_id);
CREATE INDEX idx_reviews_reviewer_status ON reviews(reviewer_id, status);
CREATE INDEX idx_feedback_requests_target_status ON feedback_requests(requested_from_user_id, status);
CREATE INDEX idx_checkins_employee_date ON checkins(employee_id, scheduled_at);
CREATE INDEX idx_tasks_assignee_status_due ON workflow_tasks(assignee_user_id, status, due_at);
CREATE INDEX idx_notifications_user_read ON notifications(user_id, read_at);
CREATE INDEX idx_audit_org_created ON audit_log(organisation_id, created_at DESC);
CREATE INDEX idx_audit_entity ON audit_log(entity_type, entity_id);
CREATE INDEX idx_ai_insights_user_cycle ON ai_insights(user_id, cycle_id);
CREATE INDEX idx_workflow_metadata_gin ON workflow_tasks USING GIN(metadata);
CREATE INDEX idx_ai_metadata_gin ON ai_insights USING GIN(metadata);

-- ============================================================================
-- UPDATED_AT TRIGGERS
-- ============================================================================

CREATE TRIGGER trg_organisations_updated BEFORE UPDATE ON organisations
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_units_updated BEFORE UPDATE ON organisational_units
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_positions_updated BEFORE UPDATE ON positions
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_users_updated BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_employee_profiles_updated BEFORE UPDATE ON employee_profiles
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_cycles_updated BEFORE UPDATE ON review_cycles
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_goals_updated BEFORE UPDATE ON goals
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_key_results_updated BEFORE UPDATE ON key_results
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_review_templates_updated BEFORE UPDATE ON review_templates
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_reviews_updated BEFORE UPDATE ON reviews
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_checkins_updated BEFORE UPDATE ON checkins
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_comments_updated BEFORE UPDATE ON comments
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_tasks_updated BEFORE UPDATE ON workflow_tasks
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

-- ============================================================================
-- REPORTING VIEWS
-- ============================================================================

CREATE VIEW vw_cycle_completion AS
SELECT
  rc.cycle_id,
  rc.organisation_id,
  rc.name AS cycle_name,
  COUNT(cp.participant_id) AS total_participants,
  COUNT(*) FILTER (WHERE cp.status = 'COMPLETED') AS completed_participants,
  ROUND(
    100.0 * COUNT(*) FILTER (WHERE cp.status = 'COMPLETED')
    / NULLIF(COUNT(cp.participant_id), 0), 2
  ) AS completion_percentage
FROM review_cycles rc
LEFT JOIN cycle_participants cp ON cp.cycle_id = rc.cycle_id
GROUP BY rc.cycle_id, rc.organisation_id, rc.name;

CREATE VIEW vw_employee_performance_summary AS
SELECT
  r.cycle_id,
  r.employee_id,
  u.display_name,
  ep.manager_user_id,
  AVG(r.goal_score) AS average_goal_score,
  AVG(r.competency_score) AS average_competency_score,
  AVG(r.overall_score) AS average_overall_score,
  COUNT(*) AS review_count
FROM reviews r
JOIN users u ON u.user_id = r.employee_id
LEFT JOIN employee_profiles ep ON ep.user_id = r.employee_id
WHERE r.status = 'COMPLETED'
GROUP BY r.cycle_id, r.employee_id, u.display_name, ep.manager_user_id;

CREATE VIEW vw_overdue_tasks AS
SELECT
  wt.workflow_task_id,
  wt.organisation_id,
  wt.task_type,
  wt.assignee_user_id,
  u.display_name AS assignee_name,
  wt.due_at,
  CURRENT_TIMESTAMP - wt.due_at AS overdue_duration
FROM workflow_tasks wt
LEFT JOIN users u ON u.user_id = wt.assignee_user_id
WHERE wt.status NOT IN ('COMPLETED','CANCELLED')
  AND wt.due_at < CURRENT_TIMESTAMP;

CREATE VIEW vw_goal_progress AS
SELECT
  g.goal_id,
  g.cycle_id,
  g.owner_user_id,
  u.display_name,
  g.title,
  g.weight,
  g.progress,
  g.status,
  g.due_date,
  COUNT(kr.key_result_id) AS key_result_count,
  AVG(kr.progress) AS average_key_result_progress
FROM goals g
JOIN users u ON u.user_id = g.owner_user_id
LEFT JOIN key_results kr ON kr.goal_id = g.goal_id
GROUP BY g.goal_id, g.cycle_id, g.owner_user_id, u.display_name,
         g.title, g.weight, g.progress, g.status, g.due_date;

-- ============================================================================
-- SEED DATA
-- ============================================================================

INSERT INTO permissions(permission_key, description) VALUES
('cycle.view','View review cycles'),
('cycle.manage','Create and manage review cycles'),
('goal.view','View goals'),
('goal.manage_self','Manage own goals'),
('goal.manage_team','Manage team goals'),
('review.submit_self','Submit self reviews'),
('review.submit_manager','Submit manager reviews'),
('review.calibrate','Perform calibration'),
('feedback.request','Request feedback'),
('feedback.submit','Submit feedback'),
('analytics.view','View analytics'),
('audit.view','View audit logs'),
('admin.manage_users','Manage users and access');

COMMIT;

-- End of schema
