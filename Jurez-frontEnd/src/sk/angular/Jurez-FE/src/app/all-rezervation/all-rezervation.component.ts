import { Component} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";

@Component({
  selector: 'app-all-rezervation',
  templateUrl: './all-rezervation.component.html',
  styleUrls: ['./all-rezervation.component.scss']
})
export class AllRezervationComponent {

    jojo: User[] | undefined;

  constructor(private userService: UserService) {
    this.reload()
  }

  reload(){
    this.userService.getAllUsersInRezervationInDay("2023-02-06 00:00:00").subscribe( value => {
      this.jojo = value;
    })
  }

}
