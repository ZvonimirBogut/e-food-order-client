package efoodorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import efoodorder.model.Order;
import efoodorder.model.OrderItem;
import efoodorder.service.GetOnConstruct;
import efoodorder.service.SendData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class App { // klasa koja pravi cijeli prozorski okvir i komponente u njoj
    private JPanel jPanel;
    private JList list1;
    private JTextField orderInput;
    private JTextField quantityInput;
    private JButton submit;
    private JLabel quantity;
    private JLabel orderItem;
    private JLabel priceValue;
    private JButton calculatePriceButton;
    private List<OrderItem> orderItems;
    private DefaultListModel<String> defaultListModel;
    private StringBuilder stringBuilder;

    public App() throws IOException { // konstruktor kroz koji se inicijalno dohvacaju podaci iz baze podataka, tocnije OrderItem da bi se prikazali u listi.
        GetOnConstruct getOnConstruct = new GetOnConstruct();
        stringBuilder = getOnConstruct.getOrderItems(); // dohvaceni podaci su tipa stringBuilder (String) i imaju mogucnost da se premapiraju u JSON
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        orderItems = objectMapper.readValue(
                stringBuilder.toString(),
                typeFactory.constructCollectionType(List.class, OrderItem.class)); // pretvaranje stringa u JSON, a zatim i u Listu koja ima tip podataka OrderItem.... List<OrderItem>

        defaultListModel = new DefaultListModel<>();

        for (OrderItem orderItem : orderItems) {
            defaultListModel.addElement(orderItem.getName() + "     " + orderItem.getPrice() + " KM");  // dodavanje elemenata List<OrderItem> u neki DefaultListModel da bi se mogli prikazati u JList komponenti
        }

        initialize(); // postavljanje komponenti za prikazivanje
    }

    private void initialize() throws IOException { // metoda koja postavlja sve komponente
        jPanel = new JPanel();

        orderInput.setPreferredSize(new Dimension(60, 30));
        quantityInput.setPreferredSize(new Dimension(60, 30));
        priceValue.setPreferredSize(new Dimension(60, 30));
        list1.setPreferredSize(new Dimension(200, 300));
        list1.add(priceValue, stringBuilder.toString());

        jPanel.add(orderItem);
        jPanel.add(orderInput);
        jPanel.add(quantity);
        jPanel.add(quantityInput);
//        jPanel.add(price);
        jPanel.add(calculatePriceButton);
        jPanel.add(priceValue);
        jPanel.add(submit);


        list1.setModel(defaultListModel);
        list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jPanel.add(list1);

        this.actionListeners(); // pozivanje action listenera na button komponente

    }

    public static void main(String[] args) throws IOException { // glavni program
        JFrame frame = new JFrame();
        frame.setContentPane(new App().jPanel);
        frame.setVisible(true);
        frame.setSize(550, 500);
        frame.setTitle("food order");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();

    }

    public void actionListeners() {
        submit.addActionListener(new ActionListener() { // listener na klik na tipku Submit.
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer quantityNumber = null;
                for (OrderItem orderItem : orderItems) {  // iteriranje kroz listu OrderItema i postavljanje novog objekta na te vrijednosti.
                    // Ako je unesena vrijednost u polje OrderItemInput ista kao i neki naziv itema koji je spremljen u bazi, tada se moze i spremiti
                    if (orderInput.getText().equals(orderItem.getName())) {
                        Order order = new Order();
                        order.setOrderItem(orderItem);
                        try {
                            quantityNumber = Integer.valueOf(quantityInput.getText()); // pretvaranje unosa teksta u quantity field u cijeli broj
                            // ako unesena vrijednost nije cijeli broj, izbacuje dodatni prozor gdje pise da se unese cijeli broj
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(quantityInput, "Enter whole number");
                            break;
                        }
                        order.setQuantity(quantityNumber);
                        SendData sendData = new SendData();
                        try {
                            sendData.saveOrderToDb(order); // ako je sve u redu, poziva se metoda za spremanje narudzbe u bazu i prikazuje se novi prozor u kojem pise da je narudza izvrsena
                            JOptionPane.showMessageDialog(submit, "Order submited");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        calculatePriceButton.addActionListener(new ActionListener() { // listener na button za izracunavanje cijene narudze.
            // Unesemo ime i kolicinu onog sto zelimo naruciti, kliknemo na tipku i prikaze nam se cijena narudzbe
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (OrderItem orderItem : orderItems) {
                    if (orderInput.getText().equals(orderItem.getName())) {
                        Double price = orderItem.getPrice();
                        Integer quantity = Integer.valueOf(quantityInput.getText());
                        priceValue.setText(String.valueOf(price * quantity));
                    }
                }
            }
        });

    }

}
