package edu.regis.msse655.annotatedbibliography.service;

import java.util.ArrayList;
import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.TypeOfMedia;

/**
 * A read-only ReferenceService implementation that provides a few references. Used to enable
 * viewing References in the master/details views until a persistence mechanism is implemented.
 */
class HardCodedReferenceService implements ReferenceService {

    List<Reference> allReferences;

    public HardCodedReferenceService() {
        createReferences();
    }

    private void createReferences() {
        allReferences = new ArrayList<>();
        Reference reference = new Reference();

        reference.setId(1);
        reference.setReferenceAbstract("The article discusses the advantages and drawbacks of programming mobile device applications (apps) using open source World Wide Web based code, or native code that is specific to each brand of smartphone or mobile computing device. The native code approach can produce apps which perform faster for 3D (three-dimensional) or image processing tasks, but few software development firms have the resources to provide separate coding for multiple devices. Information on the various programming languages used for smartphones such the iPhone, BlackBerry and Android is provided.");
        reference.setAuthors("CHARLAND, ANDRE and LEROUX, BRIAN");
        reference.setTypeOfMedia(TypeOfMedia.MAGAZINE);
        reference.setMediaTitle("Communications of the ACM");
        reference.setKeywords("APPLICATION software, PROGRAMMING languages (Electronic computers), OPEN source software, SMARTPHONES, MOBILE computing, C (Computer program language), HTML (Document markup language), MOBILE apps, DEVELOPMENT, SOFTWARE, IPHONE (Smartphone), BLACKBERRY (Smartphone), JAVA (Computer program language)");
        reference.setReferenceTitle("Mobile Application Development: Web vs. Native.");
        reference.setUrl("http://dml.regis.edu/login?url=http://search.ebscohost.com/login.aspx?direct=true&db=bth&AN=60863975&site=ehost-live&scope=site");
        reference.setDoi("10.1145/1941487.1941504");
        reference.setDetails("Number: 5 \nPages: 49-53 \nVolumen: 54 \nYear: 2011");
        allReferences.add(reference);

        reference = new Reference();
        reference.setId(2);
        reference.setReferenceAbstract("NoBot is a novel malware detection system that employs packet classification and distinct counting techniques to achieve reliable detection and identification of malware by observing the traffic to and from a network-connected host. The solution is designed to be economically incorporated into endpoint devices, such as Ethernet switches, Gigabit passive optical network (GPON) devices, and digital subscriber line access multiplexers (DSLAMs) leveraging the integral features of the hosting device, such as packet classification, packet counting, packet-forwarding features, and the computing resources of the control processor. NoBot combines these features with deep packet inspection and distinct counting to detect the presence of malware with a low rate of false positive detections. The NoBot software has been incorporated into a Linux device driver, installed into an Android-based smart phone, and implemented as a preprocessor module for the open source Snort Intrusion detection and pre");
        reference.setAuthors("Menten, Lawrence E. and Chen, Aiyou and Stiliadis, Dimitrios");
        reference.setTypeOfMedia(TypeOfMedia.JOURNAL);
        reference.setMediaTitle("Bell Labs Technical Journal (John Wiley & Sons, Inc.)");
        reference.setKeywords("COMPUTER software development, MALWARE (Computer software), ETHERNET (Local area network system), SMARTPHONES, LINUX device drivers (Computer programs), DIGITAL subscriber lines, DATA packets & packeting, PASSIVE optical networks");
        reference.setReferenceTitle("NoBot: Embedded malware detection for endpoint devices.");
        reference.setUrl("http://dml.regis.edu/login?url=http://search.ebscohost.com/login.aspx?direct=true&db=bth&AN=60731958&site=ehost-live&scope=site");
        reference.setDoi("10.1002/bltj.20492");
        reference.setDetails("Number: 1 \nPages: 155-170 \nVolume: 16 \n Year: 2011");
        allReferences.add(reference);

        reference = new Reference();
        reference.setId(3);
        reference.setReferenceTitle("App structure");
        reference.setTypeOfMedia(TypeOfMedia.WEBSITE);
        reference.setMediaTitle("Google design guidelines");
        reference.setUrl("https://www.google.com/design/spec/patterns/app-structure.html");
        allReferences.add(reference);

        reference = new Reference();
        reference.setId(4);
        reference.setReferenceTitle("Really big disaster movie");
        reference.setAuthors("Smith, J. D. (Producer), & Smithee, A. F. (Director)");
        reference.setTypeOfMedia(TypeOfMedia.UNKNOWN);
        reference.setMediaTitle("Paramount Pictures");
        allReferences.add(reference);


        /*
        Smith, J. D. (Producer), & Smithee, A. F. (Director). (2001). Really big disaster movie [Motion picture]. United States: Paramount Pictures.
         */
    }


    @Override
    public List<Reference> retrieveAllReferences() {
        return allReferences;
    }

    @Override
    public Reference retrieveReference(int id) {
        for(Reference reference : allReferences) {
            if( reference.getId() == id) {
                return reference;
            }
        }
        return null;
    }
}
