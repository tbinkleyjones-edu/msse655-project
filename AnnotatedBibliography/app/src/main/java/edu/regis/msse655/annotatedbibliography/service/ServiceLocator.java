package edu.regis.msse655.annotatedbibliography.service;

/**
 * A simple Service Locator object that abstracts service implementations.
 */
public class ServiceLocator {

    private static ServiceLocator instance;

    private ReferenceService referenceService;

    private ServiceLocator(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    private static ServiceLocator getInstance() {
        if (instance == null) {
            createDefaultInstance();
        }
        return instance;
    }

    private static void createDefaultInstance() {
        instance = new ServiceLocator(
                new HardCodedReferenceService());
    }

    /**
     * Gets a Reference Service implementation
     * @return
     */
    public static ReferenceService getReferenceService() {
        return getInstance().referenceService;
    }

}
