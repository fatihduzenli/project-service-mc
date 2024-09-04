package com.cydeo.util;

public class SwaggerExamples {

    private SwaggerExamples() {
        // Private constructor to prevent instantiation
    }

    public static final String PROJECT_CREATE_REQUEST_EXAMPLE = "{\n" +
            "  \"projectName\": \"Demo Project\",\n" +
            "  \"projectCode\": \"SP000\",\n" +
            "  \"startDate\": \"2024-01-01\",\n" +
            "  \"endDate\": \"2024-12-31\",\n" +
            "  \"projectDetail\": \"This is a sample project for demonstration purposes.\"\n" +
            "}";

    public static final String PROJECT_UPDATE_REQUEST_EXAMPLE = "{\n" +
            "  \"projectName\": \"Demo Project\",\n" +
            "  \"startDate\": \"2024-01-01\",\n" +
            "  \"endDate\": \"2024-12-31\",\n" +
            "  \"projectDetail\": \"This is a sample project for demonstration purposes.\"\n" +
            "}";

    public static final String PROJECT_GET_RESPONSE_SINGLE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Project is successfully retrieved.\",\n" +
            "  \"data\": {\n" +
            "    \"projectName\": \"Demo Project\",\n" +
            "    \"projectCode\": \"SP000\",\n" +
            "    \"assignedManager\": \"john.doe@example.com\",\n" +
            "    \"startDate\": \"2024-01-01\",\n" +
            "    \"endDate\": \"2024-12-31\",\n" +
            "    \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "    \"projectStatus\": \"OPEN\"\n" +
            "  }\n" +
            "}";

    public static final String PROJECT_CREATE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"CREATED\",\n" +
            "  \"message\": \"Project is successfully created.\",\n" +
            "  \"data\": {\n" +
            "    \"projectName\": \"Demo Project\",\n" +
            "    \"projectCode\": \"SP000\",\n" +
            "    \"assignedManager\": \"john.doe@example.com\",\n" +
            "    \"startDate\": \"2024-01-01\",\n" +
            "    \"endDate\": \"2024-12-31\",\n" +
            "    \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "    \"projectStatus\": \"OPEN\"\n" +
            "  }\n" +
            "}";

    public static final String PROJECT_UPDATE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Project is successfully updated.\",\n" +
            "  \"data\": {\n" +
            "    \"projectName\": \"Demo Project\",\n" +
            "    \"projectCode\": \"SP000\",\n" +
            "    \"assignedManager\": \"john.doe@example.com\",\n" +
            "    \"startDate\": \"2024-01-01\",\n" +
            "    \"endDate\": \"2024-12-31\",\n" +
            "    \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "    \"projectStatus\": \"OPEN\"\n" +
            "  }\n" +
            "}";

    public static final String PROJECT_COMPLETE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Project is successfully completed.\",\n" +
            "  \"data\": {\n" +
            "    \"projectName\": \"Demo Project\",\n" +
            "    \"projectCode\": \"SP000\",\n" +
            "    \"assignedManager\": \"john.doe@example.com\",\n" +
            "    \"startDate\": \"2024-01-01\",\n" +
            "    \"endDate\": \"2024-12-31\",\n" +
            "    \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "    \"projectStatus\": \"OPEN\"\n" +
            "  }\n" +
            "}";

    public static final String PROJECT_GET_RESPONSE_LIST_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Projects are successfully retrieved.\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"projectName\": \"Demo Project - 1\",\n" +
            "      \"projectCode\": \"SP000\",\n" +
            "      \"assignedManager\": \"john.doe@example.com\",\n" +
            "      \"startDate\": \"2024-01-01\",\n" +
            "      \"endDate\": \"2024-12-31\",\n" +
            "      \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "      \"projectStatus\": \"OPEN\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"projectName\": \"Demo Project - 2\",\n" +
            "      \"projectCode\": \"SP999\",\n" +
            "      \"assignedManager\": \"john.doe@example.com\",\n" +
            "      \"startDate\": \"2024-01-01\",\n" +
            "      \"endDate\": \"2024-12-31\",\n" +
            "      \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "      \"projectStatus\": \"IN_PROGRESS\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String PROJECT_DETAIL_GET_RESPONSE_LIST_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Projects are successfully retrieved.\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"projectName\": \"Demo Project - 1\",\n" +
            "      \"projectCode\": \"SP000\",\n" +
            "      \"assignedManager\": \"john.doe@example.com\",\n" +
            "      \"startDate\": \"2024-01-01\",\n" +
            "      \"endDate\": \"2024-12-31\",\n" +
            "      \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "      \"projectStatus\": \"OPEN\",\n" +
            "      \"completedTaskCount\": 0,\n" +
            "      \"nonCompletedTaskCount\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"projectName\": \"Demo Project - 2\",\n" +
            "      \"projectCode\": \"SP999\",\n" +
            "      \"assignedManager\": \"john.doe@example.com\",\n" +
            "      \"startDate\": \"2024-01-01\",\n" +
            "      \"endDate\": \"2024-12-31\",\n" +
            "      \"projectDetail\": \"This is a sample project for demonstration purposes.\",\n" +
            "      \"projectStatus\": \"IN_PROGRESS\",\n" +
            "      \"completedTaskCount\": 7,\n" +
            "      \"nonCompletedTaskCount\": 3\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String MANAGER_GET_BY_PROJECT_CODE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Manager information is successfully retrieved.\",\n" +
            "  \"data\": \"john.doe@example.com\",\n" +
            "}";

    public static final String PROJECT_COUNT_BY_MANAGER_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Project count is retrieved.\",\n" +
            "  \"data\": 5\n" +
            "}";

    public static final String PROJECT_CHECK_BY_PROJECT_CODE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Project exists.\",\n" +
            "  \"data\": true,\n" +
            "}";

    public static final String ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Access is denied\",\n" +
            "  \"httpStatus\": \"FORBIDDEN\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Access denied, make sure that you are working on your own project.\",\n" +
            "  \"httpStatus\": \"FORBIDDEN\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PROJECT_ALREADY_EXISTS_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Project already exists.\",\n" +
            "  \"httpStatus\": \"CONFLICT\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PROJECT_ALREADY_COMPLETED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Project is already completed.\",\n" +
            "  \"httpStatus\": \"CONFLICT\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PROJECT_NOT_FOUND_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Project does not exist.\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String PROJECT_DETAILS_NOT_RETRIEVED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Project details cannot be retrieved.\",\n" +
            "  \"httpStatus\": \"INTERNAL_SERVER_ERROR\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String USER_NOT_FOUND_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"User does not exist.\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String TASKS_NOT_COMPLETED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Related tasks cannot be completed.\",\n" +
            "  \"httpStatus\": \"INTERNAL_SERVER_ERROR\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String TASKS_NOT_DELETED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Related tasks cannot be deleted.\",\n" +
            "  \"httpStatus\": \"INTERNAL_SERVER_ERROR\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String VALIDATION_EXCEPTION_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Invalid Input(s)\",\n" +
            "  \"httpStatus\": \"BAD_REQUEST\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\",\n" +
            "  \"errorCount\": 1,\n" +
            "  \"validationExceptions\": [\n" +
            "    {\n" +
            "      \"errorField\": \"projectName\",\n" +
            "      \"rejectedValue\": \"SP\",\n" +
            "      \"reason\": \"Project name length should be min 3, max 16.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
