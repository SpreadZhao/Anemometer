import org.apache.logging.log4j.LogManager


fun main() {
    val logger = LogManager.getLogger()
    logger.debug("debug")
    logger.info("info")
    logger.error("error")
}