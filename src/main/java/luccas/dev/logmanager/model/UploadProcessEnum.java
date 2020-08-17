package luccas.dev.logmanager.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UploadProcessEnum {

	PROCESSING(1, "In batch process"),
	SUCCESSFULY_PROCESSED(2, "File upload with success"),
	ERROR_ON_PROCESS(3, "Error on batch process");

	private final int id;
	private final String name;

}