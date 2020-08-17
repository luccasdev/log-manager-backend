package luccas.dev.logmanager.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UploadProcessEnum {

	PROCESSING(0, "In batch process"),
	SUCCESSFULY_PROCESSED(1, "File upload with success"),
	ERROR_ON_PROCESS(2, "Error on batch process");

	private final int id;
	private final String name;

}