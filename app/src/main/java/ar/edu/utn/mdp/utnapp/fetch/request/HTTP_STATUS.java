package ar.edu.utn.mdp.utnapp.fetch.request;

public final class HTTP_STATUS {
    public static final int INFORMATIONAL_CONTINUE = 100;
    public static final int INFORMATIONAL_SWITCHING_PROTOCOLS = 101;
    public static final int INFORMATIONAL_PROCESSING = 102;
    public static final int SUCCESS_OK = 200;
    public static final int SUCCESS_CREATED = 201;
    public static final int SUCCESS_ACCEPTED = 202;
    public static final int SUCCESS_NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int SUCCESS_NO_CONTENT = 204;
    public static final int SUCCESS_RESET_CONTENT = 205;
    public static final int SUCCESS_PARTIAL_CONTENT = 206;
    public static final int SUCCESS_MULTI_STATUS = 207;
    public static final int SUCCESS_ALREADY_REPORTED = 208;
    public static final int SUCCESS_IM_USED = 226;
    public static final int REDIRECTION_MULTIPLE_CHOICES = 300;
    public static final int REDIRECTION_MOVED_PERMANENTLY = 301;
    public static final int REDIRECTION_FOUND = 302;
    public static final int REDIRECTION_SEE_OTHER = 303;
    public static final int REDIRECTION_NOT_MODIFIED = 304;
    public static final int REDIRECTION_USE_PROXY = 305;
    public static final int REDIRECTION_UNUSED = 306;
    public static final int REDIRECTION_TEMPORARY_REDIRECT = 307;
    public static final int REDIRECTION_PERMANENT_REDIRECT = 308;
    public static final int CLIENT_ERROR_BAD_REQUEST = 400;
    public static final int CLIENT_ERROR_UNAUTHORIZED = 401;
    public static final int CLIENT_ERROR_PAYMENT_REQUIRED = 402;
    public static final int CLIENT_ERROR_FORBIDDEN = 403;
    public static final int CLIENT_ERROR_NOT_FOUND = 404;
    public static final int CLIENT_ERROR_METHOD_NOT_ALLOWED = 405;
    public static final int CLIENT_ERROR_NOT_ACCEPTABLE = 406;
    public static final int CLIENT_ERROR_PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int CLIENT_ERROR_REQUEST_TIMEOUT = 408;
    public static final int CLIENT_ERROR_CONFLICT = 409;
    public static final int CLIENT_ERROR_GONE = 410;
    public static final int CLIENT_ERROR_LENGTH_REQUIRED = 411;
    public static final int CLIENT_ERROR_PRECONDITION_FAILED = 412;
    public static final int CLIENT_ERROR_PAYLOAD_TOO_LARGE = 413;
    public static final int CLIENT_ERROR_URI_TOO_LONG = 414;
    public static final int CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int CLIENT_ERROR_RANGE_NOT_SATISFIABLE = 416;
    public static final int CLIENT_ERROR_EXPECTATION_FAILED = 417;
    public static final int CLIENT_ERROR_MISDIRECTED_REQUEST = 421;
    public static final int CLIENT_ERROR_UNPROCESSABLE_ENTITY = 422;
    public static final int CLIENT_ERROR_LOCKED = 423;
    public static final int CLIENT_ERROR_FAILED_DEPENDENCY = 424;
    public static final int CLIENT_ERROR_UPGRADE_REQUIRED = 426;
    public static final int CLIENT_ERROR_PRECONDITION_REQUIRED = 428;
    public static final int CLIENT_ERROR_TOO_MANY_REQUESTS = 429;
    public static final int CLIENT_ERROR_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
    public static final int CLIENT_ERROR_UNAVAILABLE_FOR_LEGAL_REASONS = 451;
    public static final int SERVER_ERROR_INTERNAL_SERVER_ERROR = 500;
    public static final int SERVER_ERROR_NOT_IMPLEMENTED = 501;
    public static final int SERVER_ERROR_BAD_GATEWAY = 502;
    public static final int SERVER_ERROR_SERVICE_UNAVAILABLE = 503;
    public static final int SERVER_ERROR_GATEWAY_TIMEOUT = 504;
    public static final int SERVER_ERROR_HTTP_VERSION_NOT_SUPPORTED = 505;
    public static final int SERVER_ERROR_VARIANT_ALSO_NEGOTIATES = 506;
    public static final int SERVER_ERROR_INSUFFICIENT_STORAGE = 507;
    public static final int SERVER_ERROR_LOOP_DETECTED = 508;
    public static final int SERVER_ERROR_NOT_EXTENDED = 510;
    public static final int SERVER_ERROR_NETWORK_AUTHENTICATION_REQUIRED = 511;
}