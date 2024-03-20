package bddTraders;

public class ClientAsClassType {
        private String firstName;
        private String lastName;
        private String email;
        private int id;

        // No-arg constructor
        public ClientAsClassType() {
        }

        // Constructor that does not set id
        public ClientAsClassType(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.id = 0;
        }

        // Getters
        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public String getEmail() {
            return email;
        }
        public int getId() {
            return id;
        }

        // Setters
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setId(int id) {
            this.id = id;
        }
    }
