package test.javadoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JavadocMemberHelper {

	public static HashMap<String, List<String>> getMemberDocs(String fqcn) {
        HashMap<String, List<String>> jDoc = null;
        try {
            jDoc = JavadocHelper.extractClassSignaturesAndDocs(fqcn, true);
        } catch (IOException e) {
            return null;
        }
        return jDoc;
    }

    public static boolean allMembersAreDocumented(String fqcn) {
        HashMap<String, List<String>> jDoc = JavadocMemberHelper.getMemberDocs(fqcn);

        for (String sig: jDoc.keySet()) {
            if (jDoc.get(sig).size() == 0) return false;
            if (!hasAppropriateTags(jDoc.get(sig), sig, fqcn)) return false;
        }
        return true;
    }

    public static List<String> getParams(String sig) throws ArrayIndexOutOfBoundsException {
        List<String> rtn = new ArrayList<String>();
        String params = "";
        try {
            params = sig.split("\\(", 2)[1].split("\\)")[0].strip();        
        } catch (ArrayIndexOutOfBoundsException e) {}
        if (params.equals("")) return rtn;

        for (String p : params.split(",")) {
            String[] tmp = p.strip().split(" ");
            rtn.add(tmp[1]);
        }
        return rtn;
    }

    public static List<String> getThrows(String sig) throws ArrayIndexOutOfBoundsException {
        List<String> rtn = new ArrayList<String>();
        String throwsStr = "";
        try {
            throwsStr = sig.split("throws ")[1].strip();  
        } catch (ArrayIndexOutOfBoundsException e) {}
        if (throwsStr.equals("")) return rtn;

        for (String t : throwsStr.split(",")) {
            rtn.add(t.strip());
        }
        return rtn;
    }

    public static boolean returnsValue(String fqcn, String sig) throws ArrayIndexOutOfBoundsException {
        String [] parts = sig.split("\\(");
        if (parts.length == 1) return false;
        parts = parts[0].strip().split(" ");
        if (parts.length == 1) return false;
        return !parts[0].strip().equals("void");
    }

    public static boolean hasAppropriateTags(List<String> javadocLines, String sig, String fqcn) {
        List<String> throwsExpected = getThrows(sig);
        List<String> paramsExpected = getParams(sig);
        List<String> paramsFound = new ArrayList<String>();
        List<String> throwsFound = new ArrayList<String>();
        int linesBeforeTags = 0;
        boolean foundAt = false;
        boolean foundReturn = false;
        for (String line: javadocLines) {
            line = JavadocHelper.stripCommentSignifier(line);
            if (line.startsWith("@")) foundAt = true;
            if (line.startsWith("@return ") && line.split(" ").length > 1) foundReturn = true;
            if (!foundAt && line.length() > 0) ++linesBeforeTags;
            if (line.startsWith("@param")) {
                String[] parts = line.substring(6).strip().split(" ");
                if (parts.length > 1) {
                    paramsFound.add(parts[0]);
                }
            } else if (line.startsWith("@throws")) {
                String[] parts = line.substring(7).strip().split(" ");
                if (parts.length > 1) {
                    throwsFound.add(parts[0]);
                }
            }
        }
        if (linesBeforeTags < 1) return false;
        if (!paramsExpected.equals(paramsFound)) return false;
        if (!throwsExpected.equals(throwsFound)) return false;
        if (foundReturn != returnsValue(fqcn, sig)) return false;
        return true;
    }

    public static void _testmemberIsDocumented(String fqcn, String memberRegex) {
        HashMap<String, List<String>> jDoc = getMemberDocs(fqcn);
        assertNotNull(jDoc);

        // Check the expected member is documented
        String key = null;
        for (String sig: jDoc.keySet()) {
            if (sig.matches(memberRegex)) {
                key = sig;
                break;
            }
        }
        assertNotNull(key);
        assertTrue(jDoc.get(key).size() > 0);
        assertTrue(hasAppropriateTags(jDoc.get(key), key, fqcn));
	}

}