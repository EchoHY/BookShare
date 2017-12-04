package com.lq.controller;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
@Controller
@RequestMapping("/upload")
public class FileUploadController implements ServletContextAware{
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext context){
		this.servletContext = context;
	}
	@RequestMapping(value = "image", method = RequestMethod.POST)
	public void uploadDiagFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		FileItem picture = null;
		DiskFileItemFactory factory = null;
		ServletFileUpload upload = null;
		//获取文件需要上传的路径
		String path = "R:\\image";
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		logger.info("path="+path);
		request.setCharacterEncoding("utf-8");//设置编码
		//获得磁盘文件条目工厂
		factory = new DiskFileItemFactory();
		factory.setRepository(dir);
		factory.setSizeThreshold(1024*1024);
		//创建一个文件上传解析器
		upload = new ServletFileUpload(factory);
		//说明Request的类名和每个request不同
		try{
			//[]说明upload.parseRequest(request)没有产生信息
			List<FileItem> list = upload.parseRequest(request);
			//String onlycode = request.getParameter("onlycode");
			//null已经被解析过,获取不到传参
			for(FileItem item : list){
				//获取表单的属性名字
				String name = item.getFieldName();
				//如果获取的表单信息是普通的文本信息
				if(item.isFormField()){
					//获取用户具体输入的字符串
					String value = item.getString();
					request.setAttribute(name, value);
					logger.debug("name="+name+",value="+value);
				}else{
					picture = item;
				}
			}
			String onlycode = (String)request.getAttribute("onlycode");
			String num = (String)request.getAttribute("imagename");
			System.out.println(onlycode+"  "+num);
			String filetype = ".jpg";
			String fileName = "wxid"+onlycode +filetype;
			//真正写到磁盘上
			File file = new File(path,fileName);
			OutputStream out = new FileOutputStream(file);
			InputStream in = picture.getInputStream();
			int length = 0;
			byte[] buf = new byte[1024];
			//in.read(buf) 每次读到的数据放在buf数组中
			while((length = in.read(buf)) != -1){
				out.write(buf,0,length);
			}
			in.close();
			out.close();			
		}catch(FileUploadException e){
			logger.error("Could not parse multipart servlet request",e);
		}
	}
	@RequestMapping(value ="/uploading",method = RequestMethod.POST)
	private void uploading(String name,String onlycode,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException { 
        System.out.println(Thread.currentThread().getId());
		response.setContentType("application/json");
		name = getCurrentDate()+"_"+getCurrentTime().replaceAll(":", "：");
		String data = "{\"result\":\""+ name+"\"}";
		try{
			PrintWriter out = response.getWriter();
			out.write(data);
		}catch(IOException e){
			e.printStackTrace();				
		}
	}  
	public static String getCurrentDate() {  
	    String pattern = "yyyy-MM-dd";  
	    SimpleDateFormat df = new SimpleDateFormat(pattern);  
	    Date today = new Date();  
	    String tString = df.format(today);  
	    return tString;  
	} 
	public static String getCurrentTime() {  
	    String pattern = "HH:mm:ss";  
	    SimpleDateFormat df = new SimpleDateFormat(pattern);  
	    Date today = new Date();  
	    String tString = df.format(today);  
	    return tString;  
	} 	 
}
	/*PrintWriter printWriter = response.getWriter();
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
HashMap<String, Object> res = new HashMap<String, Object>();
res.put("success", true);
printWriter.write(JSONSerializer.toJSON(res).toString());
printWriter.flush();*/
/*if (!file.isEmpty()) { 
try { 
	data = "{\"result\":\"fileExist\"}";
	// 文件保存路径 
	//String filepath = this.servletContext.getRealPath("/tmp/");
	//String  filepath = "/usr/image";
	String  filepath = "R:/image";
	//String	fileName = file.getOriginalFilename();
	String fileName = "phoneNum";
	//change to phonenumber
	String	fileType = fileName.substring(fileName.lastIndexOf("."));
	int num=fileType.length();//得到后缀名长度  
	String fileOtherName=fileName.substring(0, fileName.length()-num);//得到文件名。去掉了后缀  
	fileName = fileOtherName+"_"+getCurrentDate()+"_"+getCurrentTime().replaceAll(":", "：")+fileType;
	// 转存文件 
	//找到那个rentable
	//把filename存入rentable
	file.transferTo(new File(filepath,fileName)); 
}catch(Exception e) { 
	e.printStackTrace(); 
} 
} */