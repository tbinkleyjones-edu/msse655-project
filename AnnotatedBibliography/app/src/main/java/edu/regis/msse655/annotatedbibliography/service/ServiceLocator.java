package edu.regis.msse655.annotatedbibliography.service;

/**
 * A simple Service Locator object that abstracts service implementations.
 */
public class ServiceLocator {

    private static ServiceLocator instance;

    private IReferenceService referenceService;

    private ServiceLocator(IReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    private static ServiceLocator getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ServiceLocator is not initialized. Call createInstance before calling get operations.");
        }
        return instance;
    }

    public static void createInstance(IReferenceService referenceService) {
        instance = new ServiceLocator(
                referenceService
        );
    }

    /**
     * Gets a Reference Service implementation
     *
     * @return
     */
    public static IReferenceService getReferenceService() {
        return getInstance().referenceService;
    }

}
