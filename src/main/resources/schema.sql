-- scheduleテーブルが存在したら削除
DROP TABLE IF EXISTS schedules;

-- scheduleテーブルを作成
CREATE TABLE schedules(
	
	--予定ID
	id serial PRIMARY KEY,
	
	--予定日
	schedule_date date NOT NULL,
	
	--予定
	title varchar(100) NOT NULL,
	
	--詳細
	detail text
	
);