package study.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import study.data.UpdateRequestForm;
import study.entity.IntroduceEntity;
import study.repository.IntroduceRepository;
import study.repository.IntroduceSpecifications;
import study.repository.UserRepository;

/**
 * 自己紹介サービステストクラス
 * @author イッシー
 *
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class IntroduceServiceTest {

	@Mock
	IntroduceRepository introduceRepository;
	
	@InjectMocks
	IntroduceService introduceService;
	
	@Mock
	UserRepository userRepository;
	
	@Autowired
	DataSource dataSource;
	
	UpdateRequestForm form;
	String username;
	@Value("${test.data.csv.path}")
	private String testCsvDataPath;

		
	@BeforeEach
	@DisplayName("テストコード実行前の事前準備")
	void setUp() {
		
		// 更新用のリクエストデータ作成
		byte[] fileContent = "Test file content".getBytes();
		MockMultipartFile file = new MockMultipartFile("file", "イッシー.png", "text/plain", fileContent);
		form = new UpdateRequestForm();
		form.setIcon(file);
		form.setJob("派遣社員");
		form.setLanguage("Java");
		form.setMyField("バックエンド");
		form.setOneMessage("よろしくね");
		username = "イッシー";
	}
	
	@Test
	@DisplayName("テストデータがCSV形式で投入できる")
	@Order(1)
	void insertTestData() throws Exception {
		
		IDatabaseConnection dbconn = new DatabaseDataSourceConnection(dataSource, "springtest");
		dbconn.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());		
		IDataSet dataset = new CsvDataSet(new File(testCsvDataPath));
		DatabaseOperation.DELETE_ALL.execute(dbconn, dataset);
		// コメントアウトしてる部分は必要に応じて使用する
		//DatabaseOperation.DELETE.execute(dbconn, dataset);
		//DatabaseOperation.INSERT.execute(dbconn, dataset);
	}
		
	@Test
	@DisplayName("得意分野作成できる")
	void getMyFieldsTest() {
		
		List<String> actual = introduceService.getMyFields();
		List<String> expects = Arrays.asList("フロントエンド", "バックエンド","フロントエンド・バックエンド");
		
		assertThat(expects.get(0), is(actual.get(0)));
		assertThat(expects.get(1), is(actual.get(1)));
		assertThat(expects.get(2), is(actual.get(2)));	
		
	}
	
	@Test
	@DisplayName("自己紹介一覧全検索できる")
	void getIntroducesTest() {
		// リポジトリから返却される値を自前作成
		List<IntroduceEntity> testData = new ArrayList<IntroduceEntity>();
		IntroduceEntity expectIntroduceEntity = new IntroduceEntity();
		expectIntroduceEntity.setId(1);
		testData.add(expectIntroduceEntity);
		when(introduceRepository.findAll()).thenReturn(testData);
				
		// テスト対象メソッド実行
		List<IntroduceEntity> actual = introduceService.getIntroduces();
		
		// テスト結果確認
		assertThat(testData.get(0).getId(), is(actual.get(0).getId()));		
	}
	
	@Test
	@DisplayName("自己紹介一覧全検索できない(0件)")
	void getNotIntroducesTest() {
		// リポジトリから返却される値を自前作成
		List<IntroduceEntity> testData = new ArrayList<IntroduceEntity>();
		when(introduceRepository.findAll()).thenReturn(testData);
		
		// テスト対象メソッド実行
		List<IntroduceEntity> actual = introduceService.getIntroduces();
		
		// テスト結果確認
		assertThat(testData.size(), is(actual.size()));
	}

	@Test
	@DisplayName("ログインユーザの自己紹介取得できる")
	void getIntroduceTest() {
		IntroduceEntity expectIntroduceEntity = new IntroduceEntity();
		expectIntroduceEntity.setId(1);
		expectIntroduceEntity.setJob("派遣社員");
		expectIntroduceEntity.setLanguage("Java");
		expectIntroduceEntity.setMyField("バックエンド");
		expectIntroduceEntity.setOneMessage("よろしく");
		expectIntroduceEntity.setIcon("イッシー.png");
		
		Optional<IntroduceEntity> entity = Optional.of(expectIntroduceEntity);
				
		when(introduceRepository.findById(anyInt())).thenReturn(entity);

		// テスト対象メソッド実行
		IntroduceEntity actual = introduceService.getIntroduce(1);
		
		// テスト結果確認
		assertEquals(expectIntroduceEntity, actual);
	}
	
	@Test
	@DisplayName("ログインユーザの自己紹介取得できない(例外発生確認)")
	void getNotIntroduceTest() {
		Optional<IntroduceEntity> entity = Optional.empty();

		when(introduceRepository.findById(any())).thenReturn(entity);

		// テスト対象メソッド実行し、テスト結果確認
		assertThrows(NoSuchElementException.class, () -> introduceService.getIntroduce(1));
		
	}

	@Test
	@DisplayName("キーワード検索できない(0件)")
	void getKeywordIntroducesTest() {
		// リポジトリから返却される値を自前作成
		List<IntroduceEntity> testData = new ArrayList<IntroduceEntity>();

		when(introduceRepository.findAll(IntroduceSpecifications.findByKeyWord("キーワード", 1))).thenReturn(testData);
				
		// テスト対象メソッド実行
		List<IntroduceEntity> actual = introduceService.getKeywordIntroduces("キーワード", 1);
		
		// テスト結果確認
		assertThat(testData.size(), is(actual.size()));
	}
}
