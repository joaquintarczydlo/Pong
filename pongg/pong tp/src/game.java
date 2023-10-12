import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.MatteBorder;

public class game extends JFrame implements KeyListener {

    private JPanel contentPane;
    private JPanel panel;
    private JLabel pelota;
    private JLabel jugador;
    private int cont1=0;
    private int xPelota = 0;
    private int yPelota = 100;
    private int velocidadXPelota = 10;
    private int velocidadYPelota = 10;
    private int contvidas = 3;
    private int contpuntos= 0;
    private JLabel[] vida= new JLabel[3];
    private Timer timer;

    private int xjugador = 350;
    private int yjugador = 680;
    private int movim_jugador = 15;
    private JLabel lblvidas;
    private JLabel lblpuntaje;
    private JLabel juegolbl;
    private JLabel puntosganados;
    private JButton btnreintento;
    private JButton btncerrar;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	game frame = new game();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //funcion de vidas disponibles en pantalla:
    private void mostrarVidas() {      
    		for (int i = 0; i <= 2; i++) {
    			vida[i] = new JLabel();
    			vida[i].setIcon(new ImageIcon(game.class.getResource("/imagenes/bola.png")));
    			vida[i].setBackground(new Color(199, 59, 27));
    			vida[i].setBounds(80+30*i, 28, 20, 20); // Establece la posición y el tamaño
    			contentPane.add(vida[i]);
    		}
    }
    
    public game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 0, 800, 780);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        //declaracion y llamada de funcion de vidas:
        lblvidas = new JLabel("VIDAS:");
        lblvidas.setFont(new Font("Consolas", Font.PLAIN, 20));
        lblvidas.setForeground(new Color(255, 255, 255));
        lblvidas.setBounds(10, 32, 66, 20);
        contentPane.add(lblvidas);
        mostrarVidas();
        
        //puntos en pantalla:
        lblpuntaje = new JLabel("PUNTAJE: 0");
        lblpuntaje.setForeground(Color.WHITE);
        lblpuntaje.setFont(new Font("Consolas", Font.PLAIN, 20));
        lblpuntaje.setBounds(306, 32, 198, 20);
        contentPane.add(lblpuntaje);
        
        JLabel linea = new JLabel("");
        linea.setBounds(0, 80, 784, 10);
        linea.setBorder(new MatteBorder(5, 0, 0, 0, Color.WHITE));
        contentPane.add(linea);

        //declaracion de pelota
        pelota = new JLabel();
        pelota.setIcon(new ImageIcon(game.class.getResource("/imagenes/bola.png")));
        pelota.setBounds(0, 85, 20, 20);
        pelota.setBackground(new Color(200, 106, 255));
        pelota.setOpaque(true);
        contentPane.add(pelota);

        //declaracion de rectangulo de jugador
        jugador = new JLabel();
        jugador.setBounds(50, yjugador, 100, 20);
        jugador.setBackground(new Color(200, 106, 255));
        jugador.setOpaque(true);
        contentPane.add(jugador);
		
		    	timer = new Timer(50, new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                moverPelota();
		                verificarColision();
		            }  
		        });
		        timer.start();
		    
        // Agregar el KeyListener
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
      
        //declaracion del panel de juego terminado:
        panel = new JPanel();
        panel.setBounds(230, 200, 340, 136);
        panel.setLayout(null);
        contentPane.add(panel);
        panel.setVisible(false);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void moverPelota() {
        xPelota += velocidadXPelota;
        yPelota += velocidadYPelota;

        if (xPelota < 0 || xPelota > getWidth() - 36) {
            velocidadXPelota = -velocidadXPelota;
        }
        if (yPelota < 86) {
            velocidadYPelota = -velocidadYPelota;
        } 
        //condicional para cuando el jugador no alcanza la pelota y llega al final de la ventana, se reiniciara en el medio e ira a una direccion aleatoria
            if (yPelota > getHeight()) {   		
            xPelota = getWidth() / 2 - 25;  // Centrar la pelota horizontalmente
            yPelota = getHeight() / 2 - 25; // Centrar la pelota verticalmente
            velocidadXPelota = (Math.random() > 0.5) ? 10 : -10;  // Dirección aleatoria
            velocidadYPelota = -10;  // Mover hacia arriba
            if (contvidas>0) {
        		contvidas--;
        		vida[contvidas].setVisible(false);
        	}
            System.out.print(contvidas);
        }
        pelota.setLocation(xPelota, yPelota);
        
        if (contvidas == 0) {
                timer.stop(); // termina el juego
             // panel de juego terminado, aparece al perder las 3 vidas
                panel.setVisible(true);
                
                juegolbl = new JLabel("JUEGO TERMINADO");
                juegolbl.setForeground(new Color(0, 0, 0));
                juegolbl.setFont(new Font("Consolas", Font.PLAIN, 25));
                juegolbl.setBounds(62, 11, 216, 24);
                panel.add(juegolbl);
                
                puntosganados = new JLabel("");
                puntosganados.setText("Tu puntaje fue de: "+contpuntos);
                puntosganados.setFont(new Font("Consolas", Font.PLAIN, 20));
                puntosganados.setBounds(10, 53, 293, 24);
                panel.add(puntosganados);
                
                btnreintento = new JButton("Reintentar");
                btnreintento.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		panel.setVisible(false);
                		puntosganados.setText("");
                		contpuntos=0;
                		lblpuntaje.setText("PUNTAJE: 0");
                		contvidas=3;
                		for (int i = 0; i <= 2; i++) {
                    	    vida[i].setVisible(true);
                		}
                		timer.start();
                	}
                });
                btnreintento.setBackground(new Color(0, 0, 0));
                btnreintento.setForeground(new Color(200, 106, 255));
                btnreintento.setFont(new Font("Consolas", Font.BOLD, 18));
                btnreintento.setBounds(10, 95, 135, 28);
                panel.add(btnreintento);
                
                btncerrar = new JButton("Cerrar juego");
                btncerrar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		game.this.setVisible(false);
                	}
                });
                btncerrar.setForeground(new Color(200, 106, 255));
                btncerrar.setFont(new Font("Consolas", Font.BOLD, 18));
                btncerrar.setBackground(Color.BLACK);
                btncerrar.setBounds(176, 95, 154, 28);
                panel.add(btncerrar);
        }
        
        // Mover jugador
        if(cont1==1) {
        	xjugador += movim_jugador;
        }
        if (xjugador < 0) {
            xjugador = 0;  // Limitar posicion del jugador en el lado izquierdo
        } 
        else if (xjugador > getWidth() - 116) {
            xjugador = getWidth() - 116;  // Limitar posicion del jugador en el lado derecho
        }
        jugador.setLocation(xjugador, yjugador);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	cont1=1;
        // Mover el jugador a la izquierda cuando se presiona la tecla de flecha izquierda
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (xjugador > 0) {
            	movim_jugador = -15;
            }
        }
        // Mover el jugador a la derecha cuando se presiona la tecla de flecha derecha
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (xjugador < getWidth() - 100) {
            	movim_jugador = 15;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	cont1=0;
        // Detener el movimiento del rectángulo cuando se suelta la tecla
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	movim_jugador = 0;
        }
    }

    private void verificarColision() {
        Rectangle pelotaBounds = pelota.getBounds();
        Rectangle jugadorBounds = jugador.getBounds();

        if (pelotaBounds.intersects(jugadorBounds)) {
        	contpuntos += 10; //aumentan en 10 los puntos por la colision
        	lblpuntaje.setText("PUNTAJE: "+contpuntos);
            // Verificar la dirección de la colisión y cambiar la dirección de la pelota en consecuencia
            if (xPelota + 25 < xjugador || xPelota + 25 > xjugador + 100) {
                // Colisión en los lados izquierdo o derecho del rectángulo de jugador
                velocidadXPelota = -velocidadXPelota;
            } 
            else {
                // Colisión en la parte superior o inferior del rectángulo de jugador
                velocidadYPelota = -velocidadYPelota;
            }
        }
    }
}