import {User} from "./user";

export interface Rezervation {
  id: number;
  currentTime: Date;
  status: boolean;
  user_id: User;
  count: number;
}
