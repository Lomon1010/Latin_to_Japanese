import java.awt.event.*;
import java.awt.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class La2JP extends JFrame implements ActionListener{
	//필드 정의하기
	private JTextArea inputMsg;
	private JTextArea output;
   private JTextArea recommendLabel;
   private JButton copyBtn;
   private JButton recommendcopyBtn;
   private static String recommend = "";

   //생성자
   public La2JP() {
      setSize(600,600);
      setLocation(100, 100);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setLayout(new FlowLayout());
      setTitle("로마자 to 히라가나 변환기");

      Font customFont = new Font("Monospaced", Font.PLAIN, 20);
      Font labelFont = new Font("Monospaced", Font.BOLD, 14);
    
      // 문자열을 입력할 수 있는 UI 
      inputMsg = new JTextArea(8, 35);
      inputMsg.setFont(customFont);
      inputMsg.setLineWrap(true);
      inputMsg.setWrapStyleWord(true);
      JScrollPane scrollPane = new JScrollPane(inputMsg);

      output = new JTextArea("여기에 변환된 히라가나가 출력됩니다.");
      output.setFont(labelFont);
      output.setBackground(Color.BLACK);
      output.setForeground(Color.WHITE);
      output.setEditable(false);       // 사용자가 수정 못 하게 설정
      output.setLineWrap(true);        // 자동 줄바꿈 활성화
      output.setWrapStyleWord(true);

      recommendLabel = new JTextArea("여기에 한자를 적용한 추천 문장이 출력됩니다.");
      recommendLabel.setFont(labelFont);
      recommendLabel.setBackground(Color.BLACK);
      recommendLabel.setForeground(Color.WHITE);
      recommendLabel.setEditable(false); // 사용자가 수정 못 하게 설정
      recommendLabel.setLineWrap(true);  // 자동 줄바꿈 활성화
      recommendLabel.setWrapStyleWord(true);
    
      // 전송버튼
      JButton sendBtn = new JButton("변환");
      sendBtn.setActionCommand("send");
      sendBtn.addActionListener(this);
    
      // 삭제버튼
      JButton deleteBtn = new JButton("모두 삭제");
      deleteBtn.setActionCommand("delete");
      deleteBtn.addActionListener(this);
   
      // 복사버튼
      copyBtn = new JButton("히라가나 복사");
      copyBtn.setActionCommand("copy");
      copyBtn.addActionListener(this);
      
      recommendcopyBtn = new JButton("한자 포함하는 추천 문장 복사");
      recommendcopyBtn.setActionCommand("recommendcopy");
      recommendcopyBtn.addActionListener(this);

      // 하단 패널 (버튼들)
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.add(sendBtn);
      buttonPanel.add(deleteBtn);
      buttonPanel.add(copyBtn);
      buttonPanel.add(recommendcopyBtn);
    
      // 하단 전체 패널 (버튼 + 출력)
      JPanel bottomPanel = new JPanel(new BorderLayout());
      bottomPanel.add(buttonPanel, BorderLayout.NORTH);
    
      // 두 개의 결과창을 세로로 배치하기 위한 패널
      JPanel textPanel = new JPanel(new GridLayout(2, 1)); 
      textPanel.add(output);
      textPanel.add(recommendLabel);
    
      bottomPanel.add(textPanel, BorderLayout.CENTER);
    
      add(scrollPane, BorderLayout.CENTER);
      add(bottomPanel, BorderLayout.SOUTH);
    
      setVisible(true);
   }
   
   //run 했을때 실행순서가 시작되는 main 메소드 
   public static void main(String[] args) {
      new La2JP();
   }

   public static String translate(String a) {
      String targets[] = 
      {
         "kya", "kyu", "kyo",
         "sya", "syu", "syo",
         "sha", "shu", "sho",
         "cha", "chu", "cho",
         "tya", "tyu", "tyo",
         "nya", "nyu", "nyo",
         "hya", "hyu", "hyo",
         "mya", "myu", "myo",
         "rya", "ryu", "ryo",
         "gya", "gyu", "gyo",
         "ja", "ju", "jo",
         "zya", "zyu", "zyo",
         "dya", "dyu", "dyo",
         "bya", "byu", "byo",
         "pya", "pyu", "pyo",
         "ka", "ki", "ku", "ke", "ko",
         "sa", "si", "su", "se", "so",
         "ta", "chi", "tsu", "tu", "te", "to",
         "na", "ni", "nu", "ne", "no",
         "ha", "hi", "fu", "hu", "he", "ho",
         "ma", "mi", "mu", "me", "mo",
         "ya", "yu", "yo",
         "ra", "ri", "ru", "re", "ro",
         "wa", "wo", "nn", "n",
         "ga", "gi", "gu", "ge", "go",
         "za", "zi", "zu", "ze", "zo",
         "da", "ji", "zu", "de", "do",
         "ba", "bi", "bu", "be", "bo",
         "pa", "pi", "pu", "pe", "po",
         "a", "i", "u", "e", "o",
         "k", "s", "t", "p",
         ".", ",", " "
      };
      String replacement[] = 
      {
         "きゃ", "きゅ", "きょ",
         "しゃ", "しゅ", "しょ",
         "しゃ", "しゅ", "しょ",
         "ちゃ", "ちゅ", "ちょ",
         "ちゃ", "ちゅ", "ちょ",
         "にゃ", "にゅ", "にょ",
         "ひゃ", "ひゅ", "ひょ",
         "みゃ", "みゅ", "みょ",
         "りゃ", "りゅ", "りょ",
         "ぎゃ", "ぎゅ", "ぎょ",
         "じゃ", "じゅ", "じょ",
         "じゃ", "じゅ", "じょ",
         "ぢゃ", "ぢゅ", "ぢょ",
         "びゃ", "びゅ", "びょ",
         "ぴゃ", "ぴゅ", "ぴょ",
         "か", "き", "く", "け", "こ",
         "さ", "し", "す", "せ", "そ",
         "た", "ち", "つ", "つ", "て", "と",
         "な", "に", "ぬ", "ね", "の",
         "は", "ひ", "ふ", "ふ", "へ", "ほ",
         "ま", "み", "む", "め", "も",
         "や", "ゆ", "よ",
         "ら", "り", "る", "れ", "ろ",
         "わ", "を", "ん", "ん",
         "が", "ぎ", "ぐ", "げ", "ご",
         "ざ", "じ", "ず", "ぜ", "ぞ",
         "だ", "じ", "ず", "で", "ど",
         "ば", "び", "ぶ", "べ", "ぼ",
         "ぱ", "ぴ", "ぷ", "ぺ", "ぽ",
         "あ", "い", "う", "え", "お",
         "っ", "っ", "っ", "っ",
         "。", "、", ""
      };
      for (int i = 0; i < targets.length; i++) {
         a = a.replace(targets[i], replacement[i]);
      }

      String tokanji[] = {
         "きょう",
         "げつようび", "かようび", "すいようび", "もくようび", "きんようび", "どようび", "にちようび",
         "にほんじん", "にほんご", "にほん", "にっぽん", 
         "かんこくじん", "かんこくご", "かんこく"
      };
      String kanji[] = {
         "今日",
         "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日", "日曜日",
         "日本人", "日本語", "日本", "日本",
         "韓国人", "韓国語", "韓国"
      };
      recommend = a;
      for (int i = 0; i < tokanji.length; i++) {
         recommend = recommend.replace(tokanji[i], kanji[i]);
      }

      return a;
	}

   //ActionListener 인터페이스를 구현해서 강제로 Override 한 메소드 
   @Override
   public void actionPerformed(ActionEvent e) {
      //이벤트가 발생한 버튼에 설정된 action command 읽어오기 
      String command=e.getActionCommand();
      if(command.equals("send")) {
         //JTextField에 입력한 문자열 읽어오기
    	   String msg = inputMsg.getText();
         msg = La2JP.translate(msg);
    	   output.setText(msg+"\n");
         recommendLabel.setText(recommend);
      }else if (command.equals("delete")) {
         //빈 문자열을 넣어주어서 삭제하기
    	   inputMsg.setText("");
      }else if (command.equals("copy")) {
         if (output.getText().isEmpty()) {
            return; // 출력이 비어있으면 복사하지 않음
         }
         StringSelection stringSelection = new StringSelection(output.getText());
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         clipboard.setContents(stringSelection, null);
         copyBtn.setText("복사됨!");
         // 1초 후에 버튼 텍스트를 원래대로 되돌리기
         new Thread(() -> {
            try {
               TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> copyBtn.setText("히라가나 복사"));
         }).start();
      }else if (command.equals("recommendcopy")) {
         if (recommend.isEmpty()) {
            return; // 추천 문장이 비어있으면 복사하지 않음
         }
         StringSelection stringSelection = new StringSelection(recommend);
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         clipboard.setContents(stringSelection, null);
         recommendcopyBtn.setText("복사됨!");
         // 1초 후에 버튼 텍스트를 원래대로 되돌리기
         new Thread(() -> {
            try {
               TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> recommendcopyBtn.setText("한자 포함하는 추천 문장 복사"));
         }).start();
      }
   }
}
