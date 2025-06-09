import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Complete2048 extends JFrame {
	/**
	 * 設定視窗標題、大小、位置
	 */
    private Game2048Panel gamePanel;
    private JLabel bestScoreLabel;//最佳成績標籤
    private int bestScore = 0;//record beat score
    
    public Complete2048() {
        setTitle("2048 Game");//標題
        setSize(500, 600);//標題位置
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        showStartScreen();
    }
    /**
     * 清除現有畫面
     * 顯示2048標題
     * 顯示new continue按鈕
     * 最後顯示best score
     */
    private void showStartScreen() {
        // 清除現有內容
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        // ===== 2048 標題 =====
        JLabel title = new JLabel("2048");
        title.setFont(new Font("Serif", Font.BOLD, 76));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);
        
        // ===== 按鈕區域 =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        
        JButton newGameButton = new JButton("New Game");
        JButton continueButton = new JButton("Continue");
        
        newGameButton.setBackground(new Color(69,75,80));
        continueButton.setBackground(new Color(69,75,80));
        newGameButton.setForeground(Color.WHITE);
        continueButton.setForeground(Color.WHITE);
        newGameButton.setContentAreaFilled(false);
        continueButton.setContentAreaFilled(false);
        newGameButton.setOpaque(true);
        continueButton.setOpaque(true);
        
        // 設定按鈕大小與外觀
        Dimension buttonSize = new Dimension(130, 50);
        newGameButton.setPreferredSize(buttonSize);
        continueButton.setPreferredSize(buttonSize);
        newGameButton.setFont(new Font("Serif", Font.BOLD, 18));
        continueButton.setFont(new Font("Serif", Font.BOLD, 18));
        
        // 如果沒有進行中的遊戲，禁用繼續按鈕
        continueButton.setEnabled(gamePanel != null);
        
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueButton);

        // text for rule
        JTextArea rulesText = new JTextArea(
            "Game rule: \n\n"+
            "1.Use arrow keys to move blocks.\n\n"+
            "2.Same number can be merged using arrow keys.\n\n"+
            "3.Each move is limited to 20 seconds.\n\n"
        );
        rulesText.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesText.setEditable(false);
        rulesText.setBackground(null); // 透明背景
        rulesText.setFocusable(false);
        rulesText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 增加內邊距
        
        // ===== 將按鈕和規則放入同一面板 =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(buttonPanel);
        centerPanel.add(rulesText);
        
        add(centerPanel, BorderLayout.CENTER); // 替換原本的 buttonPanel
        
        // ===== 最佳成績顯示區域 =====
        bestScoreLabel = new JLabel("Best Score: " + bestScore);
        bestScoreLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        bestScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(bestScoreLabel, BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        add(bottomPanel, BorderLayout.SOUTH);
        
        // ===== 事件監聽器 =====
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gamePanel != null) {
                    showGameScreen();
                }
            }
        });
        
        revalidate();
        repaint();
        setVisible(true);
    }
    
    /**
     * 建立新的panel
     * 再切換到遊戲畫面
     */
    private void startNewGame() {
        gamePanel = new Game2048Panel();
        showGameScreen();
    }
    /**
     * 清除畫面
     * 顯示遊戲畫面
     * 顯示menu,new game 按鈕
     * 放入game2048panel
     */
    private void showGameScreen() {
        // 清除現有內容
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        // ===== 頂部控制面板 =====
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 返回按鈕
        JButton backButton = new JButton("← Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            /**
             * 回到選單時更新最佳成績更新最佳成績
             */
            if (gamePanel.getScore() > bestScore) {
                bestScore = gamePanel.getScore();
            }
            showStartScreen();
        });
        
        // 新遊戲按鈕
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 14));
        newGameButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "確定要開始新遊戲嗎？當前進度將會丟失。",
                "確認",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                startNewGame();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);
        buttonPanel.add(newGameButton);
        
        topPanel.add(buttonPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        
        // ===== 遊戲區域 =====
        add(gamePanel, BorderLayout.CENTER);
        
        // ===== 底部說明 =====
        JLabel instructions = new JLabel("Use arrow keys to move blocks", SwingConstants.CENTER);
        instructions.setFont(new Font("Arial", Font.PLAIN, 12));
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(instructions, BorderLayout.SOUTH);
        
        // 確保遊戲面板能接收鍵盤輸入
        gamePanel.requestFocusInWindow();
        
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Complete2048();
        });
    }
}

class Game2048Panel extends JPanel {
    private static final int BOARD_SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int TILE_MARGIN = 10;
   
    private int[][] board;//4乘4棋盤
    private int score;//成績
    private boolean gameWon;//到達達到2048
    private boolean gameOver;//是否遊戲結束
    private Random random;//隨機
    private Timer timer;          // 計時器
    private int timeLeft = 10;    // 剩餘時間（秒）
    private boolean isTimedMode = true; // 是否啟用計時器
    
    // AI生成顏色配置
    private final Color BACKGROUND_COLOR = new Color(187, 173, 160);//背景顏色
    private final Color EMPTY_TILE_COLOR = new Color(205, 193, 180);
    private final Color[] TILE_COLORS = {
        new Color(238, 228, 218), // 2
        new Color(237, 224, 200), // 4
        new Color(242, 177, 121), // 8
        new Color(245, 149, 99),  // 16
        new Color(246, 124, 95),  // 32
        new Color(246, 94, 59),   // 64
        new Color(237, 207, 114), // 128
        new Color(237, 204, 97),  // 256
        new Color(237, 200, 80),  // 512
        new Color(237, 197, 63),  // 1024
        new Color(237, 194, 46)   // 2048
    };
    
    public Game2048Panel() {
        setPreferredSize(new Dimension(
            BOARD_SIZE * TILE_SIZE + (BOARD_SIZE + 1) * TILE_MARGIN,
            BOARD_SIZE * TILE_SIZE + (BOARD_SIZE + 1) * TILE_MARGIN + 60
        ));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        
        board = new int[BOARD_SIZE][BOARD_SIZE];
        random = new Random();
        score = 0;
        gameWon = false;
        gameOver = false;
        
        // 初始化計時器（每秒觸發一次）
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                repaint(); // 更新界面
                
                if (timeLeft <= 0) {
                    timer.stop();
                    gameOver = true;
                    JOptionPane.showMessageDialog(Game2048Panel.this, 
                        "时间到！游戏结束！最终得分：" + score);
                }
            }
        });
        
        // 添加鍵盤監聽器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                
                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moved = moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        moved = moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moved = moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moved = moveRight();
                        break;
                }
                
                if (moved) {
                	resetTimer();//移動後重新計時
                    addRandomTile();
                    checkGameState();
                    repaint();
                }
            }
        });
        
        initGame();
    }
    //新增方法：重置計時器
    private void resetTimer() {
        timeLeft = 20;
        if (!timer.isRunning()) {
            timer.start();
        }
    }
    
    private void initGame() {
        // 清空棋盤
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
            }
        }
        
        // 新遊戲開始時重製計時器
        if (isTimedMode) {
            timeLeft = 20;
            timer.start();
        }
        
        // 添加兩個初始方塊
        addRandomTile();
        addRandomTile();
        repaint();
    }
    
    private void addRandomTile() {
        ArrayList<Point> emptyTiles = new ArrayList<>();
        
        // 找出所有空位置
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyTiles.add(new Point(i, j));
                }
            }
        }
        
        if (!emptyTiles.isEmpty()) {
            Point randomTile = emptyTiles.get(random.nextInt(emptyTiles.size()));
            // 90% 機率生成 2，10% 機率生成 4
            board[randomTile.x][randomTile.y] = (random.nextFloat() < 0.9) ? 2 : 4;
        }
    }
    
    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            int[] row = new int[BOARD_SIZE];
            int index = 0;
            
            // 收集非零元素
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 0) {
                    row[index++] = board[i][j];
                }
            }
            
            // 合併相同數字
            for (int j = 0; j < index - 1; j++) {
                if (row[j] == row[j + 1]) {
                    row[j] *= 2;
                    score += row[j];
                    row[j + 1] = 0;
                }
            }
            
            // 重新收集非零元素
            int[] newRow = new int[BOARD_SIZE];
            index = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (row[j] != 0) {
                    newRow[index++] = row[j];
                }
            }
            
            // 檢查是否有移動
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != newRow[j]) {
                    moved = true;
                }
                board[i][j] = newRow[j];
            }
        }
        return moved;
    }
    
    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            int[] row = new int[BOARD_SIZE];
            int index = BOARD_SIZE - 1;
            
            // 從右到左收集非零元素
            for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                if (board[i][j] != 0) {
                    row[index--] = board[i][j];
                }
            }
            
            // 合併相同數字（從右到左）
            for (int j = BOARD_SIZE - 1; j > 0; j--) {
                if (row[j] != 0 && row[j] == row[j - 1]) {
                    row[j] *= 2;
                    score += row[j];
                    row[j - 1] = 0;
                }
            }
            
            // 重新收集非零元素
            int[] newRow = new int[BOARD_SIZE];
            index = BOARD_SIZE - 1;
            for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                if (row[j] != 0) {
                    newRow[index--] = row[j];
                }
            }
            
            // 檢查是否有移動
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != newRow[j]) {
                    moved = true;
                }
                board[i][j] = newRow[j];
            }
        }
        return moved;
    }
    
    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < BOARD_SIZE; j++) {
            int[] column = new int[BOARD_SIZE];
            int index = 0;
            
            // 收集非零元素
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][j] != 0) {
                    column[index++] = board[i][j];
                }
            }
            
            // 合併相同數字
            for (int i = 0; i < index - 1; i++) {
                if (column[i] == column[i + 1]) {
                    column[i] *= 2;
                    score += column[i];
                    column[i + 1] = 0;
                }
            }
            
            // 重新收集非零元素
            int[] newColumn = new int[BOARD_SIZE];
            index = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (column[i] != 0) {
                    newColumn[index++] = column[i];
                }
            }
            
            // 檢查是否有移動
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][j] != newColumn[i]) {
                    moved = true;
                }
                board[i][j] = newColumn[i];
            }
        }
        return moved;
    }
    
    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < BOARD_SIZE; j++) {
            int[] column = new int[BOARD_SIZE];
            int index = BOARD_SIZE - 1;
            
            // 從下到上收集非零元素
            for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                if (board[i][j] != 0) {
                    column[index--] = board[i][j];
                }
            }
            
            // 合併相同數字（從下到上）
            for (int i = BOARD_SIZE - 1; i > 0; i--) {
                if (column[i] != 0 && column[i] == column[i - 1]) {
                    column[i] *= 2;
                    score += column[i];
                    column[i - 1] = 0;
                }
            }
            
            // 重新收集非零元素
            int[] newColumn = new int[BOARD_SIZE];
            index = BOARD_SIZE - 1;
            for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                if (column[i] != 0) {
                    newColumn[index--] = column[i];
                }
            }
            
            // 檢查是否有移動
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][j] != newColumn[i]) {
                    moved = true;
                }
                board[i][j] = newColumn[i];
            }
        }
        return moved;
    }
    
    private void checkGameState() {
        // 檢查是否獲勝（達到2048）
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 2048 && !gameWon) {
                    gameWon = true;
                    timer.stop();//獲勝時停止計時器
                    JOptionPane.showMessageDialog(this, "恭喜！你達到了2048！");
                    return;
                }
            }
        }
        
        // 檢查遊戲是否結束
        if (isGameOver()) {
            gameOver = true;
            timer.stop();
            JOptionPane.showMessageDialog(this, "遊戲結束！最終得分：" + score);
        }
    }
    
    private boolean isGameOver() {
        // 檢查是否還有空位置
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        
        // 檢查是否還能合併
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE - 1; j++) {
                if (board[i][j] == board[i][j + 1]) return false;
            }
        }
        
        for (int j = 0; j < BOARD_SIZE; j++) {
            for (int i = 0; i < BOARD_SIZE - 1; i++) {
                if (board[i][j] == board[i + 1][j]) return false;
            }
        }
        
        return true;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 繪製分數
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + score, 10, 30);
        
        //繪製倒計時（右上角）
        g2d.setColor(timeLeft <= 3 ? Color.RED : Color.BLUE); // 最後三秒變紅色
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String timeText = "Time: " + timeLeft + "s";
        int timeTextWidth = g2d.getFontMetrics().stringWidth(timeText);
        g2d.drawString(timeText, getWidth() - timeTextWidth - 10, 30);
        
        // 繪製遊戲棋盤
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                drawTile(g2d, board[i][j], j, i);
            }
        }
    }
    
    private void drawTile(Graphics2D g2d, int value, int x, int y) {
        int tileX = TILE_MARGIN + x * (TILE_SIZE + TILE_MARGIN);
        int tileY = TILE_MARGIN + y * (TILE_SIZE + TILE_MARGIN) + 40;
        
        // 繪製方塊背景
        if (value == 0) {
            g2d.setColor(EMPTY_TILE_COLOR);
        } else {
            int colorIndex = (int) (Math.log(value) / Math.log(2)) - 1;
            if (colorIndex >= 0 && colorIndex < TILE_COLORS.length) {
                g2d.setColor(TILE_COLORS[colorIndex]);
            } else {
                g2d.setColor(TILE_COLORS[TILE_COLORS.length - 1]);
            }
        }
        
        g2d.fillRoundRect(tileX, tileY, TILE_SIZE, TILE_SIZE, 15, 15);//數字方塊的角落圓潤處理
        
        // 繪製數字
        if (value != 0) {
            g2d.setColor(value <= 4 ? new Color(119, 110, 101) : Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, value < 100 ? 36 : value < 1000 ? 30 : 24));
            
            FontMetrics fm = g2d.getFontMetrics();
            String text = String.valueOf(value);
            int textX = tileX + (TILE_SIZE - fm.stringWidth(text)) / 2;
            int textY = tileY + (TILE_SIZE + fm.getAscent()) / 2 - 2;
            
            g2d.drawString(text, textX, textY);
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public void newGame() {
        score = 0;
        gameWon = false;
        gameOver = false;
        initGame();
    }
}