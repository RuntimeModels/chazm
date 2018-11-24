package runtimemodels.chazm.model.message

enum class L(private val string: String) { // for loggers

    CHECK_NOT_NULL("Checking @NotNull for {} with {}"), //
    EXECUTION_TIME("{}: {} nanoseconds: {} {}.{}()"), //
    MAP_IS_MISSING_ENTRY("Map '{}' is missing <{}, {}> entry"), //
    MAP_IS_MISSING_KEY("Map '{}' is missing '{}' key"), //
    RECEIVED_AGENT_ATTRIBUTE_VALUE("Received ({}, {}): agent = {}, attribute = {}, value = {}"), //
    RECEIVED_AGENT_CAPABILITY_SCORE("Received ({}, {}): agent = {}, capability = {}, score = {}"), //
    RECEIVED_AGENT_ROLE_GOAL("Received ({}, {}): agent = {}, role = {}, goal = {}"), //
    RECEIVED_ID("Received ({}, {}): id = {}"), //
    RECEIVED_PMF_ATTRIBUTE("Received ({}, {}): pmf = {}, attribute = {}"), //
    RECEIVED_ROLE_ATTRIBUTE("Received ({}, {}): role = {}, attribute = {}"), //
    RECEIVED_ROLE_CAPABILITY("Received ({}, {}): role = {}, capability = {}"), //
    RECEIVED_ROLE_CHARACTERISTIC_VALUE("Received ({}, {}): role = {}, characteristic = {}, value = {}"), //
    RECEIVED_ROLE_GOAL("Received ({}, {}): role = {}, goal = {}"), //
    RECEIVED_ROLE_PMF("Received ({}, {}): role = {}, pmf = {}"), //
    START_TAG("Encountered start tag <{}>"), //
    SUBSCRIBER_ALREADY_REGISTERED("Subscriber ({}) is already registered for ({})"), //
    SUBSCRIBER_REGISTERED("Registering ({}) for ({}) with ({})"), //
    UNABLE_TO_INVOKE("Unable to invoke {}.{}({})");

    fun get(): String {
        return string
    }

}
