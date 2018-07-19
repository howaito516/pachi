//元々は、専門学校1年生時代に作った自動販売機のプログラムにスロットの機能を付けたものです
//自動販売機の機能を消し、単純なパチンコ台にリメイクしました

import java.io.*;
public class pachi{
	public static void main(String[] args)throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long KK = 0;			//大当たり継続回数
		long RAND = 0;			//ランダムな数値を出す(何度も使う)
		long KEIZOKU = 0;		//継続するかどうかのフラグ
		long TAMA = 0;			//持ち玉数
		long KARITAMA = 0;		//借りた玉数
		long COUNT = 0;			//回転数
		long ATARIflg = 0;		//大当たりフラグ
		int DAI = 0;			//台の種類選択
		String SUKA;			//一度Enterを押して貰うときに使う
		long flgX = 0;			//一時的なフラグ台選択ステップ終了フラグ
		long endflg = 0;		//完全終了フラグ
		long HUE = 0;			//その大当たりで増えた玉数
		long REEL1 = 0;			//左リールの数字
		long REEL2 = 0;			//中リールの数字
		long REEL3 = 0;			//右リールの数字
		double GEKIATSU = 0.5;	//激アツの信頼度(%)
		long GEKIATSUflg = 0;	//激アツフラグ
		long TP = 100;			//通常時の確立
		long STP = 10;			//ST中の大当たり確率　
		long ST = 10;			//ST継続回数
		long DDA1 = 424;		//出玉量(少)
		long DDA2 = 1111;		//出玉料(多)
		long DDP = 5;			//出玉量(多)になる確率
		long TK = 32;			//通常時の回転率(27で等価ボーダー18回転)
		long STK = 5;			//ST中の回転率
									

							
			while(flgX ==0){	//台選択ステップ終了フラグ
				System.out.println("座る台を選択してください( 1:100ver 2:400ver 3:沼)");
				try{			//台選択で123以外を押したとき
					DAI = Integer.parseInt(br.readLine());
				}catch(java.lang.NumberFormatException e){
					System.out.println("1 2 3 の中から選択してください");
				}	
				switch(DAI){
				case 1:			//甘
				TP = 100;		//通常時の確立
				STP = 5;		//ST中の大当たり確率
				ST = 5;			//ST継続回数
				DDA1 = 405;		//出玉量(少)
				DDA2 = 1200;	//出玉料(多)
				DDP = 5;		//出玉量(多)になる確率
				TK = 31;		//通常時の回転率(1～TK玉消費で1回転)(ボーダーは31)
				STK = 1;		//ST時の回転率(1～TK玉消費で1回転)
				flgX =1;		//台選択ステップ終了フラグ
				break;

				case 2:			//MAX
				TP = 400;		//通常時の確立
				STP = 80;		//ST中の大当たり確率
				ST = 100;			//ST継続回数
				DDA1 = 570;		//出玉量(少)
				DDA2 = 2400;	//出玉料(多)
				DDP = 50;		//出玉量(多)になる確率
				TK = 27;		//通常時の回転率(1～TK玉消費で1回転)(ボーダーは27)
				STK = 1;		//ST時の回転率(1～TK玉消費で1回転)
				flgX =1;		//台選択ステップ終了フラグ
				break;

				case 3:			//沼
				TP = 4000;		//通常時の確立
				STP = 80;		//ST中の大当たり確率　
				ST = 100;			//ST継続回数
				DDA1 = 1000;		//出玉量(少)
				DDA2 = 36500;	//出玉料(多)
				DDP = 50;		//出玉量(多)になる確率
				TK = 27;		//通常時の回転率(1～TK玉消費で1回転)(ボーダーは27)
				STK = 1;		//ST時の回転率(1～TK玉消費で1回転)
				flgX =1;		//台選択ステップ終了フラグ
				break;

				default:		//台選択で123以外を押したとき
				System.out.print("");
				}
			}				//台選択ステップ終了フラグ

			System.out.println("借りる玉数を入力してください");
			flgX = 0;
			while(flgX ==0){						//借りる玉数数値入力完了フラグ
				try{		
					TAMA = Integer.parseInt(br.readLine());
					KARITAMA = KARITAMA + TAMA;			//借りた玉数を合計しておく
					flgX = 1;
				}catch(java.lang.NumberFormatException e){
					System.out.println("数値を入力してください");
				}
			}

			TP = 10000/ TP;							//通常時の大当たり確率を再計算
			STP = 10000/ STP;						//ST時の大当たり確率を再計算

		while(endflg ==0){								//完全終了
			RAND = (int)(Math.random()*TK);				//1回転目の消費玉数を計算
				
			while(ATARIflg==0){ 						//当たりフラグが0なら通常時を通る
				if(TAMA > RAND){						//持ち玉>消費玉数かどうか判定
					TAMA = TAMA - RAND; 				//1回転分玉を減らす
					COUNT++;							//回転数1UP
					RAND = (int)(Math.random()*10000);	//通常時の大当たり計算
					if(TP / GEKIATSU > RAND){			//激アツ実装
					System.out.print("激アツ！ 心してEnterを押すのじゃあ！");
					SUKA = (br.readLine());				//1度Enterをはさむ
					GEKIATSUflg = 1;					//激アツフラグでハズレの時にEnterを一度押させるためのフラグ
					}
					if(TP > RAND){						//RANDで出した数値(9999～0)が、10000/TPした数値より小さければ初当たり

						GEKIATSUflg = 0;				//激アツフラグリセット
						COUNT = 0;						//回転数リセット
						KK++;
						RAND = (int)(Math.random()*100);				//ラウンド振り分け乱数
						if(RAND < DDP){									//ラウンド振り分け（大）
							RAND = (int)(Math.random()*100);			//オーバー入賞乱数
							TAMA = TAMA + DDA2 + RAND;					//持ち玉を増やす
							HUE = DDA2 + RAND;							//この大当たりで増える玉数を算出
							System.out.print("[7 7 7]");				//出玉大大当たりなら7揃いにする
							System.out.println("★大当たり！！！！" +KK+"回目 出玉["+ HUE +"] ST突入！！");
						}else{											//ラウンド振り分け（小）
							RAND = (int)(Math.random()*100);			//オーバー入賞乱数
							TAMA = TAMA + DDA1 + RAND;					//持ち玉を増やす
							HUE = DDA1 + RAND;							//この大当たりで増える玉数を算出
							RAND = (int)(Math.random()*5 + 1);				//全リール乱数(出玉少当たり)
							System.out.print("["+ RAND +" "+ RAND +" "+ RAND +"]");
							System.out.println("★当たり！" +KK+"回目 出玉["+HUE + "] ST突入！！");
						}
						ATARIflg = 1;				//ST突入フラグ
						SUKA = (br.readLine());	//1度Enterをはさむ
					}else{
						REEL1 = (int)(Math.random()*6+1);//左リール乱数(出玉少当たり)
						REEL2 = (int)(Math.random()*6+1);//中リール乱数(出玉少当たり)
						REEL3 = (int)(Math.random()*6+1);//右リール乱数(出玉少当たり)
						if(REEL2 == REEL3){//はずれなのに3つ数字が揃わないようにする
							REEL3++;
							if(REEL3 == 8){
								REEL3 = 1;
							}
						}
						if(GEKIATSUflg == 1){ 												//激アツフラグがかかった状態ならEnterを一度挟ませる
							System.out.print("["+ REEL1 +" "+ REEL2 +" "+ REEL3+"]");		 	//はずれの時のリール表記
							System.out.println("残念！はずれ 残り玉数["+TAMA+"] 回転数["+COUNT+"] ");	//はずれの時の表記
							RAND = (int)(Math.random()*TK);									//2回転目以降の消費玉数算出
							SUKA = (br.readLine());	//1度Enterをはさむ
							GEKIATSUflg = 0;
						}else{
							System.out.print("["+ REEL1 +" "+ REEL2 +" "+ REEL3+"]");
							System.out.println("はずれ 残り玉数["+TAMA+"] 回転数["+COUNT+"] ");	//はずれの時の表記
							RAND = (int)(Math.random()*TK);									//2回転目以降の消費玉数算出
						}
					}	
					
				}else{
				TAMA = 0;															//消費玉数より持ち玉数が少なかった場合、持ち玉を0にする
				System.out.println("玉がなくなりました 総投資["+KARITAMA*4+"]");	//玉が無くなった時の表記
				System.out.println("借りる玉数を入力してください");
				flgX = 0;
				while(flgX ==0){	//数値入力完了フラグ
					try{		
						TAMA = Integer.parseInt(br.readLine());
						flgX = 1;
					}catch(java.lang.NumberFormatException e){
						System.out.println("数値を入力してください");
					}	
				}			
				KARITAMA = KARITAMA + TAMA;			//借りた玉数合計に加算
				}
			}

			while(ATARIflg==1){								//当たりフラグがONならST突入
				while(COUNT < ST){							//規定ST数を超えるまで継続
					TAMA = TAMA - STK;						//ST時の消費玉数
					COUNT++;								//回転数1UP
						
					RAND = (int)(Math.random()*10000);		//ST中の大当たり判定
					if(STP > RAND){							//ST中の大当たり判定
						KK++;								//ST継続数1UP
						COUNT = 0;							//回転数リセット
						RAND = (int)(Math.random()*100);	//ラウンド振り分け乱数
						if(RAND < DDP){						//ラウンド振り分け（大）
							RAND = (int)(Math.random()*100);//オーバー入賞乱数
							TAMA = TAMA + DDA2 +RAND;		//持ち玉を増やす
							HUE = DDA2 + RAND;				//この大当たりで増える玉数を算出
							System.out.print("[7 7 7]");	//出玉大大当たりなら7揃いにする
							System.out.println("【ST中!】★大当たり！！！！ (" +KK+"回継続中) 出玉["+  HUE + "] ST継続！");
						}else{								//ラウンド振り分け（小）
							RAND = (int)(Math.random()*100);//オーバー入賞乱数
							TAMA = TAMA + DDA1 + RAND;		//持ち玉を増やす
							HUE = DDA1 + RAND;				//この大当たりで増える玉数を算出
							RAND = (int)(Math.random()*5 + 1);	//全リール乱数(出玉少当たり)
							System.out.print("["+ RAND +" "+ RAND +" "+ RAND +"]");//リール揃い（1～6）
							System.out.println("【ST中!】★当たり！ (" +KK+"回継続中)  出玉["+ HUE +"] ST継続！");
						}

					}else{
					REEL1 = (int)(Math.random()*6+1);//左リール乱数(出玉少当たり)
					REEL2 = (int)(Math.random()*6+1);//中リール乱数(出玉少当たり)
					REEL3 = (int)(Math.random()*6+1);//右リール乱数(出玉少当たり)
					if(REEL2 == REEL3){//はずれなのに3つ数字が揃わないようにする
						REEL3++;
						if(REEL3 == 8){
							REEL3 = 1;
						}
					}
					System.out.print("["+ REEL1 +" "+ REEL2 +" "+ REEL3+"]");
					System.out.println("【ST中!】はずれ 残り玉数["+TAMA+"] 回転数["+COUNT+"] ");	//ST中のはずれ表記

					}	
					SUKA = (br.readLine());				//一度Enterをはさむ
				}	
				ATARIflg = 0;							//当たりフラグ終了
				KK = 0;									//大当たり継続数リセット
				System.out.println("ST終了");			
				System.out.println("続けるならEnter やめるなら1を入力 ");

				flgX = 0;
				while(flgX ==0){	//継続or終了フラグ
					try{
						KEIZOKU = Integer.parseInt(br.readLine());
						flgX = 1;
					}catch(java.lang.NumberFormatException e){
					flgX = 1;
					}
				}

				if(KEIZOKU == 1){
					endflg = 1;			//完全終了
							
				}							//規定ST数を超えるまで継続の終わり
			}								//当たりフラグがONならST突入の終わり
		}	
		
		
		//以下、リザルト
		System.out.println("  回収["+ TAMA * 4+"]");
		System.out.println("総投資[-"+KARITAMA * 4+"]");
		System.out.println("--------------------");
		System.out.print("    計 [");
		System.out.print(TAMA * 4 - KARITAMA * 4);
		System.out.print("]");
	}

}