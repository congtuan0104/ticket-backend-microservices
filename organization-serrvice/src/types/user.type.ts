export interface User {
  id: string;
  avatar: string;
  email: string;
  name: string;
  phoneNumber: string;
  role: "user" | "organize";
}
