//package com.example.user.firstai;
//
//
//// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
//
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.net.URI;
//
//public class faceDetect {
//    // Replace <Subscription Key> with your valid subscription key.
//    private static final String subscriptionKey = "<Subscription Key>";
//
//    // NOTE: You must use the same region in your REST call as you used to
//// obtain your subscription keys. For example, if you obtained your
//// subscription keys from westus, replace "westcentralus" in the URL
//// below with "westus".
////
//// Free trial subscription keys are generated in the "westus" region. If you
//// use a free trial subscription key, you shouldn't need to change this region.
//    private static final String uriBase =
//            "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";
//
//    private static final String imageWithFaces =
//            "{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}";
//
//    private static final String faceAttributes =
//            "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";
//
//    public static void main(String[] args) {
//        HttpClient httpclient = HttpClients.createDefault();
//
//
//        try {
//            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/face/v1.0/detect");
//
//            builder.setParameter("returnFaceId", "true");
//            builder.setParameter("returnFaceLandmarks", "false");
//            builder.setParameter("returnFaceAttributes", "{string}");
//
//            URI uri = builder.build();
//            HttpPost request = new HttpPost(uri);
//            request.setHeader("Content-Type", "application/json");
//            request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");
//
//
//            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");
//            request.setEntity(reqEntity);
//
//            HttpResponse response = httpclient.execute(request);
//            HttpEntity entity = response.getEntity();
//
//            if (entity != null) {
//                System.out.println(EntityUtils.toString(entity));
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
//
//
//
