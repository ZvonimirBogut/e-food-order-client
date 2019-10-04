package efoodorder.model;

import java.util.Set;

public class Customer {

        private Integer id;

        private Integer phone;

        private String email;

        private String firstName;

        private String lastName;

        private Set<Order> orders;

        private Set<Payment> payment;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPhone() {
            return phone;
        }

        public void setPhone(Integer phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Set<Order> getOrders() {
            return orders;
        }

        public void setOrder(Set<Order> orders) {
            this.orders = orders;
        }

        public Set<Payment> getPayment() {
            return payment;
        }

        public void setPayment(Set<Payment> payment) {
            this.payment = payment;
        }

}
