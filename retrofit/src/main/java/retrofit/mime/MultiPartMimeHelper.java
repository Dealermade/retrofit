package retrofit.mime;

import java.util.List;

public class MultiPartMimeHelper {
    public static List<byte[]> getParts(MultipartTypedOutput output) {
        try {
            return output.getParts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MultipartTypedOutput newMultipart(String boundary) {
        return new MultipartTypedOutput(boundary);
    }
}
