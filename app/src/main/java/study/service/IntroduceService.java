package study.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import study.data.UpdateRequestForm;
import study.entity.IntroduceEntity;
import study.repository.IntroduceRepository;
import study.repository.IntroduceSpecifications;
import study.repository.UserRepository;

/**
 * 自己紹介に関するビジネスロジッククラス
 * ①：全件検索
 * ②：ユーザ指定検索
 * ③：キーワード検索
 * ④：得意分野のセレクト値作成
 * ⑤：自己紹介更新
 * @author イッシー
 *
 */
@Service
public class IntroduceService {

	@Autowired
	private IntroduceRepository introduce;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 画像ファイルアップロードディレクトリ(本番環境)
	 */
	@Value("${upload.directory}")
	private String uploadDir;

	/**
	 * 画像ファイルアップロードディレクトリ(ローカル)
	 */
	@Value("${upload.directory.local}")
	private String uploadDirLocal;
	
	/**
	 * 開発環境フラグ
	 */
	@Value("${develop.env}")
	private String isDevelopEnv;
	
	/**
	 * 本番環境フラグ
	 */
	private static String IS_PRODUCTION = "1";

	
	/**
	 * 自己紹介更新処理
	 * @param form 自己紹介リクエスト情報
	 * @param username 認証が通っているユーザ名
	 * @throws IOException 
	 */
	@Transactional(rollbackFor = RuntimeException.class)
	public void update(UpdateRequestForm form, String username) {
		
		Path filePath = uploadFile(form.getIcon(), username);
						
		IntroduceEntity entity = userRepository.findByName(username).getIntroduceEntity();
		entity.setJob(form.getJob());
		entity.setLanguage(form.getLanguage());
		entity.setOneMessage(form.getOneMessage());
		entity.setMyField(form.getMyField());
		entity.setIcon(filePath.getFileName().toString());

		introduce.flush();
	}
	
	/**
	 * ファイルアップロード処理を行う
	 * @param iconFile アイコンファイル
	 * @param username 認証済のユーザ名
	 * @return アップロードファイル情報
	 */
	private Path uploadFile(MultipartFile iconFile, String username) {
		
		Path filePath;
		
		// 開発環境毎でアップロード先を変更する
		if (IS_PRODUCTION.equals(isDevelopEnv)) {
			filePath = Path.of(uploadDir, username +".png");
		} else {
			filePath = Path.of(uploadDirLocal, username +".png");
		}
				
		try {
			Files.copy(iconFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("ファイルアップロードエラーです。システム屋に問い合わせしてください" + e.getCause(), e.getCause());
		}

		return filePath;
	}
	
	/**
	 * 自己紹介をすべて取得
	 * @return 自己紹介リスト
	 */
	public List<IntroduceEntity> getIntroduces() {
		return introduce.findAll();
	}
	
	/**
	 * 自己紹介IDに紐づく自己紹介を取得
	 * @param id 自己紹介ID
	 * @return 更新用自己紹介データ(1件)
	 */
	public IntroduceEntity getIntroduce(int id) {
		return introduce.findById(id).orElseThrow();
	}
	
	/**
	 * キーワード検索した自己紹介を取得する
	 * @param keyword キーワード
	 * @return 自己紹介リスト
	 */
	public List<IntroduceEntity> getKeywordIntroduces(String keyword, int communityId) {	
		return introduce.findAll(IntroduceSpecifications.findByKeyWord(keyword, communityId));
	}
	
	/**
	 * 得意分野リストを取得する
	 * @return 得意分野リスト
	 */
	public List<String> getMyFields() {
		return Arrays.asList("フロントエンド","バックエンド","フロントエンド・バックエンド");
	}	
}
