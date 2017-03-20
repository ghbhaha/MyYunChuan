package suda.myyunchuan.util;

/**
 * 类MovieUtil，提供了一些静态方法，用来获取用户最近一次 使用云窗所播放的影片的Id和下载地址。
 *
 * @author psrain
 */
public class MovieUtil {

//
//    /**
//     * 根据给定的movieId和contentNum（封装在movieInfo中），以及服务器IP，查找对应的下载地址
//     *
//     * @param movieInfo 保存了movieId和contentNum的Map对象
//     * @param hostIP    服务器IP
//     * @return 该函数返回Map<String, String>类型的对象，包含2个键"highResolution"和
//     * "lowResolution"。
//     * <br>对于电影，键"highResolution"的值为该电影的高分辨率版本，键"lowResolution"
//     * 的值对应该电影的低分辨率版本。
//     * <br>对于其他类型（剧集、综艺、讲座等），由于云窗只提供一种版本，所以将这些类型的影片的下载地址
//     * 放入键"highResolution"的值中，而键"lowResolution"的值为空字符串。
//     * @throws DocumentException 若需要访问XML文件在服务器上不存在时，抛出该异常
//     * @throws IOException       若需要访问的XML文件的元素不存在时，抛出该异常
//     */
//    public static Map<String, String> getMovieAddressByInfo(Map<String, String> movieInfo,
//                                                            String hostIP)
//            throws DocumentException, IOException {
//        //movieAddress用来当做该函数的结果返回
//        Map<String, String> movieAddress = new HashMap<String, String>();
//        movieAddress.put("highResolution", "");
//        movieAddress.put("lowResolution", "");
//
//        SAXReader reader = new SAXReader();
//        reader.setEncoding("GBK");
//        Document doc;
//        doc = reader.read(new URL("http://" + hostIP + "/mov/"
//                + movieInfo.get("movieId") + "/film.xml"));
//        Element root = doc.getRootElement();
//
//        //若film.xml中没有元素"Contentnumber"，则该函数无法继续执行，此时应抛出异常
//        if (root.element("Contentnumber") == null) {
//            throw new IOException();
//        }
//
//        //film.xml中的"Contentnumber"元素，记录了该影片共有多少集，对于电影，该值通常为2（高分辨率与低分辨率），
//        //对于剧集，则为实际的集数，对于其他类型（讲座等），该值通常为1
//        int contentSize = Integer.parseInt(root.element("Contentnumber").getTextTrim());
//
//        //每个movieId对应的下载地址，都放在对应的url2.xml的"c"元素中
//        doc = reader.read(new URL("http://" + hostIP + "/mov/"
//                + movieInfo.get("movieId") + "/url2.xml"));
//        root = doc.getRootElement();
//
//        //若url2.xml中没有元素"c"，则该函数无法继续执行，此时应抛出异常
//        if (root.element("c") == null) {
//            throw new IOException();
//        }
//        String tmp = root.element("c").getTextTrim();
//
//        //读取到的"c"元素的值是以逗号分隔的
//        String[] addresses = tmp.split(",", -1);
//
//        //若该movieId对应的不是剧集
//        if (contentSize == 1 || contentSize == 2) {
//            //因为从"c"元素中，读取到的其实是服务器上的本地路径（如E:\xxx\xxx.mp4）
//            //所以下面的for循环把本地路径转换为下载地址
//            for (int i = 0; i < addresses.length; i++) {
//                if (!addresses[i].isEmpty()) {
//                    addresses[i] = "http://"
//                            + hostIP
//                            + "/kuu"
//                            + addresses[i].charAt(0)
//                            + "/"
//                            + addresses[i].substring(addresses[i].lastIndexOf("\\") + 1);
//                }
//            }
//
//            //若该movieId对应的不是电影
//            if (contentSize == 1) {
//                movieAddress.put("highResolution", addresses[0]);
//            }
//            //若该movieId对应的是电影
//            else {
//                //对于电影，通常可以从"c"元素中，读取到2个下载地址，第一个是低分辨率，第二个是高分辨率
//                //但也有可能只有1个下载地址，此时第二个地址为空串
//                if (addresses[1].isEmpty()) {
//                    movieAddress.put("highResolution", addresses[0]);
//                } else {
//                    movieAddress.put("highResolution", addresses[1]);
//                    movieAddress.put("lowResolution", addresses[0]);
//                }
//            }
//        }
//        //若该movieId对应的是剧集，则此时还需要知道movieInfo中的"contentNum"
//        else if (contentSize > 2) {
//            int contentNum = Integer.parseInt(movieInfo.get("contentNum"));
//            addresses[contentNum] = "http://" + hostIP + "/kuu"
//                    + addresses[contentNum].charAt(0) + "/"
//                    + addresses[contentNum].substring(addresses[contentNum].lastIndexOf("\\") + 1);
//            movieAddress.put("highResolution", addresses[contentNum]);
//        }
//        return movieAddress;
//    }

}
