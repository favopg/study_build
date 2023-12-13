package study.data;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author イッシー
 *
 */
@Setter
@Getter
public class UpdateRequestForm {
	
	/** 得意分野 */
	@NotBlank(message = "その操作は不正ですよ")	
	private String myField;
	
	/** アイコン画像 */
	private MultipartFile icon;
	
	/** 一言メッセージ  */
	@NotBlank(message = "最近のブームを教えてください")
	private String oneMessage;

	/** 仕事 */
	@NotBlank(message = "現在の仕事内容を教えてください")
	private String job;

	/** 言語 */
	@NotBlank(message = "業務で使用したことあるプログラミング言語を教えてください")	
	private String language;
	
}
