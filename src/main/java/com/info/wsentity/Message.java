package com.info.wsentity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
	
	private String senderName;
	private String receiverName;
	private String message;
	private String groupId;
	private String date;
	private Status status;
}
