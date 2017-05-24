/**
 * 
 */
package com.ymt;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ymt.pz365.framework.core.utils.SpringBoot;

/**
 * @author zhailiang
 *
 */
@SpringBootApplication
public class MjqWebApplication {

	public static void main(String[] args) throws Exception {
		SpringBoot.start(MjqWebApplication.class, args);
//		SpringApplication application = new SpringApplication(MjqWebApplication.class);
//		String[] profiles = {"dev"};
//		if(ArrayUtils.isNotEmpty(args)){
//			profiles = StringUtils.splitByWholeSeparatorPreserveAllTokens(args[0], ",");
//		}
//		System.out.println(new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"系统启动中,profiles:"+ArrayUtils.toString(profiles));
//		application.setAdditionalProfiles(profiles);
//		ConfigurableApplicationContext applicationContext = application.run(args);
//		
//		PosterWeixinMessageProcessor messageProcessor = applicationContext.getBean(PosterWeixinMessageProcessor.class);
//		
//		ReceivedMessage message = new ReceivedMessage();
//		message.setFromUserName("oua4YwKiGeNNC4-VjcDjIzbs4TWk");
//		message.setMsgType("event");
//		message.setEvent("subscribe");
//		
//		messageProcessor.process(message);
//		
//		System.out.println("系统启动完毕.Ctrl+C或者输入exit退出");
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		try {
//		    String input = reader.readLine();
//            while (!StringUtils.equals("exit", input)) {
//                input = reader.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}

}
