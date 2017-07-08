/*
 * 	This file is part of DicomFlow.
 *
 * 	DicomFlow is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 *
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 *
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.ufpb.dicomflow.integrationAPI.mail.impl;

import java.util.StringTokenizer;

import br.ufpb.dicomflow.integrationAPI.exceptions.ContentBuilderException;

public class MailXTags {

	public static final String HEAD_BUILDER_X_TAG = "X-Head-Builder";
	public static final String CONTENT_BUILDER_X_TAG = "X-Content-Builder";
	public static final String MESSAGE_ID_X_TAG = "X-Message-ID";
	public static final String SERVICE_TYPE_X_TAG = "X-Service-Type";

	public static final String FROM = "From";
	public static final String TO = "To";
	public static final String SUBJECT = "Subject";
	public static final String DATE = "Date";


	//Notification Mechanism Tags
	public static final String DISPOSITION_NOTIFICATION_TO_X_TAG = "X-Disposition-Notification-To";
	public static final String DISPOSITION_NOTIFICARION_KEY_ID_X_TAG = "X-Disposition-Notification-Key-ID";

	public static String buildMessageIDXTag(String messageID, String domain){
		return messageID +"@"+ domain;

	}

	public static String getMessageID(String messageIDXTag) throws ContentBuilderException{
		StringTokenizer tokenizer = new StringTokenizer(messageIDXTag,"@");
		if(tokenizer.countTokens() == 2){
			return tokenizer.nextToken();
		}else{
			throw new ContentBuilderException("X-Message-ID invalid format");
		}
	}

	public static String getDomain(String messageIDXTag) throws ContentBuilderException{
		StringTokenizer tokenizer = new StringTokenizer(messageIDXTag,"@");
		if(tokenizer.countTokens() == 2){
			tokenizer.nextToken();
			return tokenizer.nextToken();
		}else{
			throw new ContentBuilderException("X-Message-ID invalid format");
		}
	}
}
