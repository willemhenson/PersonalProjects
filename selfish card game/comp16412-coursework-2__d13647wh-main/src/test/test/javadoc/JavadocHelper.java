package test.javadoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


class JavadocHelper {

    public static Path fqcnToFilePath(String fqcn) throws IOException {
        // Note that split takes in a regex so we have to escape the dot here
        String[] elems = fqcn.split("\\.");
        elems[elems.length-1] += ".java";

        // Varargs would be nice here, but since we'll either have 2 or 3
        // elements it's not essential
        if (elems.length != 2 && elems.length !=3) {
          throw new IllegalArgumentException();
        }

        // Nasty hack because we're not running from inside the src directory
        List<String> tmp = new ArrayList<String>();
        tmp.add("src");
        tmp.add("main");
        Collections.addAll(tmp, elems);
        elems = tmp.toArray(new String[tmp.size()]);

        // Combination of the above two nasty hacks :(
        Path p = Paths.get(elems[0], elems[1], elems[2], elems[3]);
        if (elems.length > 4) {
          p = Paths.get(elems[0], elems[1], elems[2], elems[3], elems[4]);
        }

        return p.toAbsolutePath();
    }

    // Caveat: will only work if the signature is all on one line!
    // I don't think this is problematic given that students see whether or
    // not the test is failing and can easily adjust their code accordingly.
    public static String extractJavadocableSignature(String line) {
      line = line.strip();
      if (lineContainsModifiers(line)) {
          if (line.startsWith("public ") || line.startsWith("protected ")) {
              while (lineContainsModifiers(line)) {
                  line = line.split(" ", 2)[1];
              }
              if (line.endsWith("{")) {
                  StringBuilder sb = new StringBuilder(line);
                  sb.deleteCharAt(line.length() - 1);
                  line = sb.toString().strip();
              }
              if (line.endsWith(";")) {
                  StringBuilder sb = new StringBuilder(line);
                  sb.deleteCharAt(line.length() - 1);
                  line = sb.toString().split("=", 2)[0].strip();
              }
              return line;
          }

      }
      return null;
    }

    public static String stripCommentSignifier(String line) {
        line = line.strip();
        if (line.endsWith("*/")) {
            line = line.substring(0, line.length()-2);
        }
        if (line.startsWith("/**")) {
            line = line.substring(3);
        } else if (line.startsWith("*")) {
            line = line.substring(1);
        }
        return line.strip();
    }

    public static boolean isAnnotation(String line) {
        line = line.strip();
        boolean simpleAnnotation = line.equals("@FunctionalInterface") || line.equals("@Override") || line.equals("@SafeVarargs");
        return simpleAnnotation || line.startsWith("@Deprecated") || line.startsWith("@SuppressWarnings");
    }

    public static boolean lineContainsModifiers(String line) {
        line = line.strip();
        boolean visibility = line.startsWith("public ") || line.startsWith("protected ") || line.startsWith("private ");
        boolean nonaccess = line.startsWith("final ") || line.startsWith("abstract ");
        if (!line.contains("class ")) {
            nonaccess = nonaccess || line.startsWith("static ") || line.startsWith("synchronized ");
            nonaccess = nonaccess || line.startsWith("transient ") || line.startsWith("volatile ");  
        }
        return visibility || nonaccess;
    }

    public static HashMap<String, List<String>> extractClassSignaturesAndDocs(String fqcn, boolean members) throws IOException {
        Path path = fqcnToFilePath(fqcn);
        List<String> lines = Collections.emptyList();
        lines = Files.readAllLines(path);
        Collections.reverse(lines);

        HashMap<String, List<String>> rtn = new HashMap<String, List<String>>();

        Iterator<String> itr = lines.iterator();
        String line = "";
        String currentMember = null;
        List<String> docLines = new ArrayList<String>();
        while (itr.hasNext()) {
            line = itr.next().strip();
            String sig = extractJavadocableSignature(line);
            boolean skipOverride = members ? isOverride(fqcn, sig) : false;
            boolean skipMemberType = false;
            if (sig != null) {
                skipMemberType = sig.contains("class") && members;
                skipMemberType = skipMemberType || (!sig.contains("class") && !members);    
            }
            boolean skip = skipOverride || skipMemberType;

            if (sig != null || line.endsWith(";") || line.startsWith("}")) {
                if (currentMember != null && !members) {
                    Collections.reverse(docLines);
                    rtn.put(currentMember, docLines);
                } else if (currentMember != null && members) {
                    Collections.reverse(docLines);
                    rtn.put(currentMember, docLines);
                }
                currentMember = skip ? null : sig;
                docLines = new ArrayList<String>();
            }
            if (sig == null && currentMember != null & line != "" & !isAnnotation(line)) {
              docLines.add(line);
            }
        }

        return rtn;
    }

    public static boolean isOverride(String fqcn, String sigToCheck) throws IOException {
        Path path = fqcnToFilePath(fqcn);
        List<String> lines = Collections.emptyList();
        lines = Files.readAllLines(path);
        Collections.reverse(lines);

        String line;
        Iterator<String> itr = lines.iterator();
        boolean found = false;
        while (itr.hasNext()) {
            line = itr.next().strip();
            String sig = extractJavadocableSignature(line);
            if (sig != null && sig.equals(sigToCheck)) {found = true; continue;}
            if (found) {
                if (!isAnnotation(line)) break;
                if (line.equals("@Override")) return true;
            }
        }
        return false;
    }

  }