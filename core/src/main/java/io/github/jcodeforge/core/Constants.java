package io.github.jcodeforge.core;

import java.awt.*;
import java.io.File;

public class Constants {

    public static final String APP_USER_DIR = System.getProperty("user.home") + File.separator +
            ".codeforge" + File.separator;

    /**
     * Names of database tables
     */
    public static final String USER_TABLE_NAME = "user_tbl";
    public static final String CHECKOUT_TABLE_NAME = "checkout_tbl";
    public static final String MAPPING_TABLE_NAME = "mapping_tbl";
    public static final String LOCATION_TABLE_NAME = "location_tbl";
    public static final String APIKEY_TABLE_NAME = "apikey_tbl";
    public static final String CLIENT_TABLE_NAME = "client_tbl";
    public static final String CONTACT_PERSON_TABLE_NAME = "contact_person_tbl";
    public static final String CUSTOMER_TABLE_NAME = "customer_tbl";
    public static final String ISSUE_TABLE_NAME = "issue_tbl";
    public static final String LICENSE_TABLE_NAME = "license_tbl";
    public static final String ACTIVATED_LICENSE_TABLE_NAME = "activated_license_tbl";
    public static final String PRODUCT_TABLE_NAME = "product_tbl";

    /**
     * Names of data model fields
     */
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_CLIENT_ID = "client_id";
    public static final String FIELD_NAME_ISSUE = "issue";
    public static final String FIELD_NAME_RESOURCE_ID = "resource_id";
    public static final String FIELD_NAME_MAPPING_ID = "mapping_id";
    public static final String FIELD_NAME_LICENSE_ID = "license_id";
    public static final String FIELD_NAME_TAG = "tag";
    public static final String FIELD_NAME_DATA = "data";
    public static final String FIELD_NAME_COMMENT = "comment";
    public static final String FIELD_NAME_CREATED_AT = "created_at";
    public static final String FIELD_NAME_UPDATED_AT = "updated_at";
    public static final String FIELD_NAME_CREATED_FILES = "created_files";
    public static final String FIELD_NAME_VERSION = "version";

    public static final String FIELD_NAME_CREATED_FORMS = "created_forms";
    public static final String FIELD_NAME_FORM_HIERARCHY = "form_hierarchy";
    public static final String FIELD_NAME_FORM_DESIGN_ID = "form_design_id";
    public static final String FIELD_NAME_FORM_OUTPUT_KEY = "form_output_key";
    public static final String FIELD_NAME_FORM_FIELD_CONFIG = "form_field_config";
    public static final String FIELD_NAME_FORM_TYPE = "form_type";
    public static final String FIELD_NAME_FORM_NAME = "form_name";
    public static final String FIELD_NAME_FORM_VALIDATION = "form_validation";
    public static final String FIELD_NAME_FORM_VALUE_KEY = "value_key";
    public static final String FIELD_NAME_FORM_START_PARAM = "form_start_param";
    public static final String FIELD_NAME_PRODUCT_PARAM = "product_param";

    public static final String FIELD_NAME_CHECKOUT_ID = "checkout_id";
    public static final String FIELD_NAME_WORKFLOW = "workflow";
    public static final String FIELD_NAME_FORM_CONFIG = "form_config";
    public static final String FIELD_NAME_MAPPING = "mapping";
    public static final String FIELD_NAME_TYPE = "type";
    public static final String FIELD_NAME_MAPPING_TYPE = "mapping_type";
    public static final String FIELD_NAME_STATUS = "status";
    public static final String FIELD_NAME_FORM_FIELD_ID = "field_id";
    public static final String FIELD_NAME_NEXT_FORM_OUTPUT_KEYS = "next_form_output_keys";
    public static final String FIELD_NAME_REFERENCE_KEY = "reference_key";
    public static final String FIELD_NAME_REFERENCE_TABLE = "reference_table";
    public static final String FIELD_NAME_REFERENCE_FIELD = "reference_field";
    public static final String FIELD_NAME_REFERENCE_NAME = "reference_name";
    public static final String FIELD_NAME_PIPELINE_NAME = "pipeline_name";
    public static final String FIELD_NAME_FORM_HIERARCHY_LEVEL = "hierarchy_level";
    public static final String FIELD_NAME_HIERARCHY_BRANCH = "hierarchy_branch";
    public static final String FIELD_NAME_HIERARCHY_ITEM_NAME = "hierarchy_item_name";
    public static final String FIELD_NAME_HIERARCHY_NODE = "hierarchy_node";
    public static final String FIELD_NAME_MAPPING_ITEM = "mapping_item";
    public static final String FIELD_NAME_FORM_FLAG = "form_flag";
    public static final String FIELD_NAME_MAPPING_FLAG = "mapping_flag";
    public static final String FIELD_NAME_LABEL = "label";

    public static final String FIELD_NAME_ADDRESS = "address";
    public static final String FIELD_NAME_ADDRESS2 = "address2";
    public static final String FIELD_NAME_CITY = "city";
    public static final String FIELD_NAME_POSTAL_CODE = "postal_code";
    public static final String FIELD_NAME_COUNTRY = "country";
    public static final String FIELD_NAME_CREATED_BY = "created_by";
    public static final String FIELD_NAME_UPDATED_BY = "updated_by";
    public static final String FIELD_NAME_CREATED_FILE_ID = "created_file_id";
    public static final String FIELD_NAME_ACTIVITIES = "activities";
    public static final String FIELD_NAME_TOKEN = "token";
    public static final String FIELD_NAME_DESCRIPTION = "description";
    public static final String FIELD_NAME_USER = "user";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_CLIENT_NAME = "client_name";
    public static final String FIELD_NAME_CONTACT_PERSON = "contact_person";
    public static final String FIELD_NAME_USERNAME = "username";
    public static final String FIELD_NAME_FIRST_NAME = "first_name";
    public static final String FIELD_NAME_LAST_NAME = "last_name";
    public static final String FIELD_NAME_EMAIL = "email";
    public static final String FIELD_NAME_PHONE_NUMBER = "phone_number";
    public static final String FIELD_NAME_PHONE_NUMBER_2 = "phone_number_2";
    public static final String FIELD_NAME_DEPARTMENT = "department";
    public static final String FIELD_NAME_NOTE = "note";

    public static final String FIELD_NAME_CUSTOMER_NAME = "customer_name";
    public static final String FIELD_NAME_CUSTOMER_NAME_2 = "customer_name_2";
    public static final String FIELD_NAME_URL = "url";
    public static final String FIELD_NAME_CATEGORY = "category";
    public static final String FIELD_NAME_CUSTOMER_ID = "customer_id";
    public static final String FIELD_NAME_TITLE = "title";
    public static final String FIELD_NAME_LOCATION = "location";
    public static final String FIELD_NAME_ASSIGNEE = "assignee";
    public static final String FIELD_NAME_PRODUCT_ID = "product_id";
    public static final String FIELD_NAME_SELECTED_FEATURES = "selected_features";
    public static final String FIELD_NAME_PASSWORD = "password";
    public static final String FIELD_NAME_START_DUE_DATE = "start_due_date";
    public static final String FIELD_NAME_END_DUE_DATE = "end_due_date";
    public static final String FIELD_NAME_CLIENT_PATH = "client_path";

    public static final String FIELD_NAME_PRODUCT_KEY = "product_key";
    public static final String FIELD_NAME_HOLDER = "holder";
    public static final String FIELD_NAME_VALID_AFTER_DATE = "valid_after_date";
    public static final String FIELD_NAME_EXPIRING_DATE = "expiring_date";
    public static final String FIELD_NAME_NUMBER_OF_LICENSES = "number_of_licenses";
    public static final String FIELD_NAME_NUMBER_OF_VEHICLES = "number_of_vehicles";
    public static final String FIELD_NAME_SIGNATURE = "signature";

    /**
     * Default ui element constants
     */

    public static final Font DEFAULT_FONT = new Font("Roboto Cn", Font.PLAIN, 24);
    public static final Font SMALL_FONT = new Font("Roboto Cn", Font.PLAIN, 16);
    public static final Font TINY_FONT = new Font("Roboto Cn", Font.PLAIN, 12);

    public static final int DEFAULT_FRAME_HEIGHT = 600;
    public static final int DEFAULT_FRAME_WIDTH = 700;

    public static final int DEFAULT_BUTTON_WIDTH = 24;
    public static final int DEFAULT_BUTTON_HEIGHT = 24;
    public static final int SMALL_BUTTON_WIDTH = 18;
    public static final int SMALL_BUTTON_HEIGHT= 18;

    public enum FrameLocation {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    /**
     * Issue categories, labels and defaults
     */
    public static final String ISSUE_CATEGORY_OPEN = "Offen";
    public static final String ISSUE_CATEGORY_CLOSED = "Beendet";
    public static final String ISSUE_CATEGORY_IN_PROGRESS = "In Bearbeitung";
    public static final String ISSUE_CATEGORY_POSTPONED = "Zurückgestellt";

    public static final String[] ISSUE_CATEGORIES = {
            ISSUE_CATEGORY_OPEN, ISSUE_CATEGORY_CLOSED, ISSUE_CATEGORY_IN_PROGRESS,
            ISSUE_CATEGORY_POSTPONED
    };

    public static final String ISSUE_LABEL_OFFER = "Angebot ";
    public static final String ISSUE_LABEL_MAILING = "Mailing";
    public static final String ISSUE_LABEL_PHONE_CALL = "Anruf";
    public static final String ISSUE_LABEL_POTENTIAL_BUYER = "Interessent";

    public static final String[] ISSUE_LABELS = {
            ISSUE_LABEL_OFFER, ISSUE_LABEL_MAILING, ISSUE_LABEL_PHONE_CALL, ISSUE_LABEL_POTENTIAL_BUYER
    };

    /**
     * Activity names, categories and defaults
     */
    public static final String ACTIVITY_CATEGORY_CLOSED = "Beendet";
    public static final String ACTIVITY_CATEGORY_IN_PROGRESS = "In Bearbeitung";
    public static final String ACTIVITY_CATEGORY_POSTPONED = "Zurückgestellt";

    public static final String[] ACTIVITY_CATEGORIES = {
            ACTIVITY_CATEGORY_CLOSED, ACTIVITY_CATEGORY_IN_PROGRESS, ACTIVITY_CATEGORY_POSTPONED
    };

    public static final String ACTIVITY_NAME_REMOTE_MAINTENANCE = "Fernwartung";
    public static final String ACTIVITY_NAME_PHONE_CALL = "Telefon";
    public static final String ACTIVITY_NAME_MAILING = "E-Mail";
    public static final String ACTIVITY_NAME_OTHER = "Sonstiges";

    public static final String[] ACTIVITY_NAMES = {
            ACTIVITY_NAME_REMOTE_MAINTENANCE, ACTIVITY_NAME_PHONE_CALL, ACTIVITY_NAME_MAILING,
            ACTIVITY_NAME_OTHER
    };

    /**
     * Customer category constants
     */
    public static final String CUSTOMER_CATEGORY_NAME_POTENTIAL_BUYER = "Interessent";
    public static final String CUSTOMER_CATEGORY_NAME_CUSTOMER_WITHOUT_SERVICE = "Kunde ohne Pflegevertrag";
    public static final String CUSTOMER_CATEGORY_NAME_CUSTOMER = "Kunden";
    public static final String CUSTOMER_CATEGORY_NAME_CUSTOMER_OLD = "Frühere Kunden";
    public static final String CUSTOMER_CATEGORY_NAME_MAILING = "Mailing";
    public static final String CUSTOMER_CATEGORY_NAME_BDE = "BDE";
    public static final String CUSTOMER_CATEGORY_NAME_CLOSED = "Geschlossen";
    public static final String CUSTOMER_CATEGORY_NAME_ACQUISITION = "Akquise";

    public static final String[] CUSTOMER_CATEGORY_NAMES = {
            CUSTOMER_CATEGORY_NAME_POTENTIAL_BUYER, CUSTOMER_CATEGORY_NAME_CUSTOMER_WITHOUT_SERVICE,
            CUSTOMER_CATEGORY_NAME_CUSTOMER, CUSTOMER_CATEGORY_NAME_CUSTOMER_OLD,
            CUSTOMER_CATEGORY_NAME_MAILING, CUSTOMER_CATEGORY_NAME_BDE, CUSTOMER_CATEGORY_NAME_CLOSED,
            CUSTOMER_CATEGORY_NAME_ACQUISITION
    };
}
