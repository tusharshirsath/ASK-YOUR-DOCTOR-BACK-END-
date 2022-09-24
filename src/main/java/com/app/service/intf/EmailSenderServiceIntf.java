package com.app.service.intf;

public interface EmailSenderServiceIntf {

	void sendEmailOnAppointmentBooking(Long patientId,String time);
	
	void sendEmailOnCancelAppointment(Long appointmentId);
	
	void sendEmailTokenToResetPassword(String userEmail, Long token); 
}
