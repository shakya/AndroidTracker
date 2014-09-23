//package test;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//import com.android.communication.SMSReceiveService;
//import com.android.data.GlobalData;
//import com.android.data.Requester;
//import com.android.data.Trackee;
//
//import dalvik.annotation.TestTarget;
//
//public class testM {
//
//	@Test
//	public void test1() {
//		SMSReceiveService smsReceiver = new SMSReceiveService();
//		Requester requesterFromMethod = smsReceiver.manageRequest(
//				"ATrckrRequst#Shakya", "0718616760");
//		Requester createdRequester = new Requester("Shakya", "0718616760");
//		assertTrue(requesterFromMethod.equals(createdRequester));
//		// GlobalData globalData = new GlobalData();
//		// ArrayList<Trackee> trackees = globalData.getTrackees();
//		// smsreciever.manageLocationData(
//		// "ATrckrLocation#3.00#56.778#78.09#23.89", "0112345654");
//		// Trackee trackee = new Trackee("");
//		// //assertTrue();
//
//	}
//}
