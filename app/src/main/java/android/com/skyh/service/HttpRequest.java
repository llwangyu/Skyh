package android.com.skyh.service;

import android.com.skyh.entity.EventSend;
import android.com.skyh.until.StringUtils;
import android.com.skyh.until.Util;
import android.graphics.Bitmap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-17.
 */
public class HttpRequest {
	final static int BUFFER_SIZE = 4096;
	final static String Tag = "RequestHttp";

    /**
     * Get请求
     * @param url
     * @param param
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	public static InputStream GET(String url, Object param) throws IOException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException, IllegalAccessException {

		String tokenUrl = url;

		HashMap<Object, Object> map = getMapObject(param);
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				sb.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
			tokenUrl = tokenUrl + "&"
					+ sb.toString().substring(0, sb.toString().length() - 1);
		}

		HttpGet httpGet = new HttpGet(tokenUrl);
		System.out.println(tokenUrl);
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			return entity.getContent();
		}
		return null;
	}

    /**
     * Post请求
     * @param url
     * @param param
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	public static InputStream POST(String url, Object param)
			throws IOException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException {
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		HashMap<Object, Object> map = getMapObject(param);
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (key != null && value != null) {
					p.add(new BasicNameValuePair(key.toString(), value
							.toString()));
				}
				// p.add(new BasicNameValuePair(entry.getKey().toString(), entry
				// .getValue().toString()));
				sb.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		HttpPost httpPost = null;
		httpPost = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(p, "UTF-8");
		httpPost.setEntity(entity);
		System.out.println(url + "?" + sb.toString());
      //  httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpClient client = HttpsUtil.getNewHttpClient();
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();

		}
		return null;
	}

    public static InputStream GET_WITHOUR_TOKEN(String url, Object param)
            throws IOException, InvocationTargetException,
            NoSuchMethodException, InstantiationException,
            IllegalAccessException {

        HashMap<Object, Object> map = getMapObject(param);
        StringBuffer sb = new StringBuffer();
        if (map != null) {
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            url = url + "&"
                    + sb.toString().substring(0, sb.toString().length() - 1);
        }

        HttpGet httpGet = new HttpGet(url);
//		System.out.println(url);
        HttpClient httpClient = HttpsUtil.getNewHttpClient();
        HttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        }
        return null;
    }

	public static InputStream postLongTimeOut(String url, Object param)
			throws IOException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException {
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		HashMap<Object, Object> map = getMapObject(param);
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (key != null && value != null) {
					p.add(new BasicNameValuePair(key.toString(), value
							.toString()));
				}
				// p.add(new BasicNameValuePair(entry.getKey().toString(), entry
				// .getValue().toString()));
				sb.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		HttpPost httpPost = null;
		httpPost = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(p, "UTF-8");
		httpPost.setEntity(entity);
		System.out.println(url + "?" + sb.toString());
		HttpClient client = HttpsUtil.getNewHttpClient();
		HttpResponse response = client.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;
	}

	/*
	 * POST
	 *
	 * @param url
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public static InputStream POSTJson(String url, String json)
			throws Exception {
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new StringEntity(json, "UTF-8");
		request.setEntity(entity);
		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpClient client = new DefaultHttpClient(httpParameters);
		HttpResponse response = client.execute(request);
		Util.print("POST:  " + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;
	}

	/*
	 * 将InputStream转换成String
	 *
	 * @param in InputStream
	 *
	 * @return String
	 *
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		String str = new String(outStream.toByteArray(), "UTF-8");
		outStream.close();
		return str;
	}

	public static HashMap<Object, Object> getMapObject(Object object)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		if (object == null) {
			return null;
		}
		Class<?> classType = object.getClass();
		Object objectCopy = classType.getConstructor(new Class[] {})
				.newInstance(new Object[]{});
		Field[] fields = classType.getDeclaredFields();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// public的方法不处理
			if (Modifier.toString(field.getModifiers()).contains("public")) {
				continue;
			}
			String fieldName = field.getName();
			if (fieldName.equals("serialVersionUID")) {
				continue;
			}

			String getName;
			String setName;
			if (policyField(fieldName)) {
				getName = "get" + fieldName;
				setName = "set" + fieldName;
			} else {
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				getName = "get" + stringLetter + fieldName.substring(1);
				setName = "set" + stringLetter + fieldName.substring(1);
			}
			Method getMethod = classType.getMethod(getName, new Class[] {});
			Method setMethod = classType.getMethod(setName,
					new Class[] { field.getType() });
			Object value = getMethod.invoke(object, new Object[] {});

			map.put(fieldName, value);
		}
		return map;
	}

	private static boolean policyField(String fieldName) {
		if (fieldName.length() < 2) {
			return false;
		}
		String firLetter = fieldName.substring(0, 1);
		String secLetter = fieldName.substring(1, 2);
		if (firLetter.toLowerCase().equals(firLetter)
				&& secLetter.toUpperCase().equals(secLetter)) {
			// 第一个小写，第二个大写，
			return true;
		}
		return false;
	}

	public static InputStream postImageNew(String url, Object object)
			throws Exception {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		EventSend eventSend = (EventSend) object;
		HttpPost httpPost = new HttpPost(url);
		if (eventSend != null) {
			HashMap<String, String> stringParamMap = eventSend.getStringParamMap();
			if (stringParamMap != null) {
				Iterator iter = stringParamMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					try {
						String key = (String) entry.getKey();
						String value = (String) entry.getValue();
						if (StringUtils.isNotNull(key) && StringUtils.isNotNull(value)) {
							StringBody sb = new StringBody(value, Charset.forName("UTF-8"));
							entity.addPart(key, sb);
						//	Util.print("key----------------" + key + ":" + sb);
							Util.print("key----------------"+key +":"+ value);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
//			if (StringUtils.isNotNull(eventSend.getFile())) {
//				String[] files = eventSend.getFile().split(",");
//				if (files.length > 0) {
////					for (String path : files) {
////						File file = new File(path);
////						entity.addPart("file", new FileBody(file, "image/*"));
////					}
//					for (int i=0;i<files.length;i++){
//						File file = new File(files[i]);
//						entity.addPart("file"+(i+1), new FileBody(file, "image/*"));
//						System.out.println("执行到这里" +files[i]);
//					}
//				}
//			}
//			if (StringUtils.isNotNull(eventSend.getVideoUploads())) {
//				String[] files = eventSend.getVideoUploads().split(",");
//				if (files.length > 0) {
//					for (String path : files) {
//						File file = new File(path);
//						entity.addPart("file", new FileBody(file, "video/*"));
//					}
//				}
//			}
//			if (StringUtils.isNotNull(eventSend.getVidiofile())) {
//				String[] files = eventSend.getVidiofile().split(",");
//				if (files.length > 0) {
//					for (String path : files) {
//						File file = new File(path);
//						entity.addPart("file1", new FileBody(file, "audio/wav"));
//					}
//				}
//			}
		}
		httpPost.setEntity(entity);
		httpPost.setHeader("Content_Type", "multipart/form-data");
//	System.out.println("执行到这里"+url+eventSend);
	//Util.printLog(url + "?" + entity.toString());
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;
	}
	public static InputStream postImage(String url, Object object)
			throws Exception {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
        System.out.println(url);
//
//        FoodFublish eventSend=(FoodFublish)object;
	HttpPost httpPost = new HttpPost(url);
//        entity.addPart("mc",new StringBody(eventSend.getMc(), Charset.forName("UTF-8")));
//        entity.addPart("ms",new StringBody(eventSend.getMs(), Charset.forName("UTF-8")));
//        entity.addPart("jg",new StringBody(eventSend.getJg(), Charset.forName("UTF-8")));
//        entity.addPart("sellerid",new StringBody(eventSend.getSellerid(), Charset.forName("UTF-8")));
//
//        if (StringUtils.isNotBlank(eventSend.getTp())) {
//            String[] files = eventSend.getTp().split(",");
//            for (String path : files) {
//                File file = new File(path);
//                entity.addPart("file", new FileBody(file, "image/*"));
//            }
 //       }


        httpPost.setEntity(entity);
        System.out.println("执行到这里"+url);
		//httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
  			return response.getEntity().getContent();
		}

		return null;

	}

	public static InputStream postImage1(String url, Object object)
			throws Exception {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		System.out.println(url);

//		Courier eventSend=(Courier)object;
	HttpPost httpPost = new HttpPost(url);
//		entity.addPart("xm",new StringBody(eventSend.getXm(), Charset.forName("UTF-8")));
//		entity.addPart("xb",new StringBody(eventSend.getXb(), Charset.forName("UTF-8")));
//		entity.addPart("sfzh",new StringBody(eventSend.getSfzh(), Charset.forName("UTF-8")));
//		entity.addPart("lxdh",new StringBody(eventSend.getLxdh(), Charset.forName("UTF-8")));
//		entity.addPart("psw",new StringBody(eventSend.getPsw(), Charset.forName("UTF-8")));
//
//		entity.addPart("sellerid",new StringBody(eventSend.getSellerid(), Charset.forName("UTF-8")));
//
//		if (StringUtils.isNotBlank(eventSend.getFile1())) {
//			String[] files = eventSend.getFile1().split(",");
//			for (String path : files) {
//				File file = new File(path);
//				entity.addPart("file1", new FileBody(file, "image/*"));
//			}
//		}


		httpPost.setEntity(entity);
	//	System.out.println("执行到这里"+url+eventSend);
		//httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}

		return null;

	}
	public static InputStream postImage2(String url, Object object)
			throws Exception {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		System.out.println(url);

//		Register register=(Register)object;
		HttpPost httpPost = new HttpPost(url);
//		entity.addPart("mc",new StringBody(register.getMc(), Charset.forName("UTF-8")));
//		entity.addPart("fwfl",new StringBody(register.getFwfl(), Charset.forName("UTF-8")));
//		entity.addPart("orgid",new StringBody(register.getOrgid(), Charset.forName("UTF-8")));
//		entity.addPart("dz",new StringBody(register.getDz(), Charset.forName("UTF-8")));
//		entity.addPart("lb",new StringBody(register.getLb(), Charset.forName("UTF-8")));
//		entity.addPart("fzr",new StringBody(register.getFzr(), Charset.forName("UTF-8")));
//		entity.addPart("sfzh",new StringBody(register.getSfzh(), Charset.forName("UTF-8")));
//		entity.addPart("lxdh",new StringBody(register.getLxdh(), Charset.forName("UTF-8")));
//		entity.addPart("pwd",new StringBody(register.getPwd(), Charset.forName("UTF-8")));
//		entity.addPart("op",new StringBody(register.getOp(), Charset.forName("UTF-8")));
//		entity.addPart("jyxkz",new StringBody(register.getJyxkz(), Charset.forName("UTF-8")));
//		entity.addPart("lng",new StringBody(register.getLng(), Charset.forName("UTF-8")));
//		entity.addPart("lat",new StringBody(register.getLat(), Charset.forName("UTF-8")));
//		entity.addPart("frxm", new StringBody(register.getFrxm(), Charset.forName("UTF-8")));
//		entity.addPart("frsfzh", new StringBody(register.getFrsfzh(), Charset.forName("UTF-8")));
//		entity.addPart("fwsjq", new StringBody(register.getFwsjq(), Charset.forName("UTF-8")));
//		entity.addPart("fwsjz", new StringBody(register.getFwsjz(), Charset.forName("UTF-8")));
//
//
//		if (StringUtils.isNotBlank(register.getFileLogo())) {
//			String files = register.getFileLogo();
//
//				File file = new File(files);
//				entity.addPart("fileLogo", new FileBody(file, "image/*"));
//
//		}


		httpPost.setEntity(entity);
//		System.out.println("执行到这里"+url+register);
		//httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}

		return null;

	}
	public static InputStream postImage3(String url, Object object)
			throws Exception {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		System.out.println(url);

	//	Courier eventSend=(Courier)object;
		HttpPost httpPost = new HttpPost(url);
//		entity.addPart("xm",new StringBody(eventSend.getXm(), Charset.forName("UTF-8")));
//		entity.addPart("xb",new StringBody(eventSend.getXb(), Charset.forName("UTF-8")));
//		entity.addPart("sfzh",new StringBody(eventSend.getSfzh(), Charset.forName("UTF-8")));
//		entity.addPart("lxdh",new StringBody(eventSend.getLxdh(), Charset.forName("UTF-8")));
//		entity.addPart("psw",new StringBody(eventSend.getPsw(), Charset.forName("UTF-8")));
//
//		entity.addPart("sellerid",new StringBody(eventSend.getSellerid(), Charset.forName("UTF-8")));
//
//		if (StringUtils.isNotBlank(eventSend.getFile1())) {
//			String[] files = eventSend.getFile1().split(",");
//			for (String path : files) {
//				File file = new File(path);
//				entity.addPart("file1", new FileBody(file, "image/*"));
//			}
//		}


		httpPost.setEntity(entity);
	//	System.out.println("执行到这里"+url+eventSend);
		//httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}

		return null;

	}
	public static InputStream postMoreFile(String url, Object object)
			throws IOException {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(entity);
		httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;

	}

	public static InputStream postSign(String url, Object object)
			throws IOException {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		MultipartEntity entity = new MultipartEntity();
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(entity);
		httpPost.setHeader("Content_Type", "multipart/form-data");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;

	}

	public static InputStream uploadFile(String urlStr, Object object)
			throws ClientProtocolException, IOException {
//		UploadPhoto uploadPhoto = (UploadPhoto) object;
//		urlStr = addToken(urlStr) + "&" + "fileUpload=%5Bobject+File%5D";
//		System.out.println(urlStr);
//		Bitmap bitmap = uploadPhoto.getPhoto();
//		if (bitmap == null) {
			return null;
//		}
//		return uploadImage(urlStr, bitmap);
	}

	public static final String BOUNDARY = "7cd4a6d158c";
	public static final String MP_BOUNDARY = "--" + BOUNDARY;
	public static final String END_MP_BOUNDARY = "--" + BOUNDARY + "--";

	private static InputStream uploadImage(String urlstr, Bitmap bitmap)
			throws IOException {
		String end = "\r\n";
		ByteArrayOutputStream bos = null;
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		HttpPost httpPost = new HttpPost(urlstr);
		bos = new ByteArrayOutputStream(1024 * 50);
		httpPost.setHeader("Content-Type", "multipart/form-data"
				+ "; boundary=" + BOUNDARY);

		StringBuilder temp = new StringBuilder();
		temp.append(MP_BOUNDARY).append(end);
		temp.append(
				"Content-Disposition: form-data; name=\"fileUpload\"; filename=\"")
				.append("fileupload.jpeg").append(end);
		String filetype = "image/jpeg";
		temp.append("Content-Type: ").append(filetype).append(end + end);

		byte[] res = temp.toString().getBytes();
		BufferedInputStream bis = null;
		try {
			bos.write(res);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			InputStream its = new ByteArrayInputStream(baos.toByteArray());

			/* 设置每次写入1024bytes */
			byte[] buffer = new byte[1024];

			int length = -1;
			while ((length = its.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				bos.write(buffer, 0, length);
			}

			bos.write(("\r\n" + END_MP_BOUNDARY).getBytes());
			bos.write(("\r\n").getBytes());
		} catch (IOException e) {
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}

		byte[] data = bos.toByteArray();
		bos.close();
		ByteArrayEntity formEntity = new ByteArrayEntity(data);

		httpPost.setEntity(formEntity);

		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}
		return null;

	}

	public static InputStream uploadFile1(String urlStr, Object object)
			throws IOException {
//		UploadPhoto uploadPhoto = (UploadPhoto) object;
		StringBuffer params = new StringBuffer();
		InputStream is = null;
		params.append("fileUpload=").append("%5Bobject+File%5D");
//		params.append("&token=").append(uploadPhoto.getToken());
		params.append("&lang=").append("");
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		URL url = new URL(urlStr + "?" + params);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* 设置传送的method=POST */

		con.setRequestMethod("POST");

		/* setRequestProperty */
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// InputStream its=uploadPhoto.getPhoto();

		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
//		ds.writeBytes("Content-Disposition: form-data; "
//				+ "name=\"fileUpload\";filename=\""
//				+ uploadPhoto.getPhotoName() + end);
		ds.writeBytes("Content-Type: image/jpeg" + end + end);
		// ds.writeBytes(end);

		// ds.write(uploadPhoto.getPhoto());
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

		/* close streams */

		ds.flush();
		ds.close();

		/* 关闭DataOutputStream */
		/* 取得Response内容 */
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			/* 取得Response内容 */
			is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */
			Util.print("上传成功" + b.toString().trim());

		} else {
			Util.print("请求服务器失败:  " + responseCode);
		}
		// char[] data = new char[1024 * 1024];
		// InputStream
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// con.getInputStream(), "UTF-8"));
		// int len = br.read(data);
		// String resultStr = String.valueOf(data, 0, len);
		return is;

	}

	public static File downLoadFile(String fileUrl, String storePath) {
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		HttpGet httpGet = new HttpGet(fileUrl);
		Util.print("fileUrl:  " + fileUrl);
		Util.print("storePath:  " + storePath);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream is = httpResponse.getEntity().getContent();
			File storeFile = new File(storePath);

			String pathString = storeFile.getPath();
			String nameString = storeFile.getName();
			File floderFile = new File(pathString.substring(0,
					pathString.length() - nameString.length()));
			if (!floderFile.exists()) {
				floderFile.mkdir();
			}

			storeFile.createNewFile();
			FileOutputStream output = new FileOutputStream(storeFile);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}

			is.close();
			output.flush();
			output.close();
			return storeFile;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// e.printStackTrace();
			return null;
		}
	}

	/*
	 * add this method for wifi
	 */
	public static String GET_String(String url) throws IOException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException, IllegalAccessException {
		if (url == null) {
			return null;
		}
		HttpGet httpGet = new HttpGet(url);
		Util.print("get string url: " + url);
		HttpClient httpClient = HttpsUtil.getNewHttpClient();
		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return InputStreamTOString(in);
		}
		return null;
	}



}
