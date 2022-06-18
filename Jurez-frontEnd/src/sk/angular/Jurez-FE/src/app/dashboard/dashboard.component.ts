import { Component} from '@angular/core';
import {Router} from "@angular/router";
import {RezervationService} from "../rezervation.service";
import {Rezervation} from "../rezervation";
import {UserService} from "../user.service";
import {User} from "../user";
import {Observable} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  items: Rezervation[] | undefined;
  item = {} as Rezervation;
  days: string[] = ['Nedeľa', 'Pondelok', 'Utorok', 'Streda', 'Štvrtok', 'Piatok', 'Sobota'];
  user$: Observable<User | undefined>;

  constructor(private rezervationService: RezervationService
    , private router: Router
    , private userService: UserService) {
    this.user$ =this.userService.onUserChange()
    if (this.user$){
      this.reload();
    }
  }

  reload(){
    this.rezervationService.getfreeRezervations().subscribe(value => {this.items = value})
    // console.log(this.item)
    // console.log(this.item.currentTime.getTime())
    // console.log("nech ti nejebe")
    // this.userService.get().subscribe((value: User) => {
    //   console.log("hodnota")
    //   console.log(value)
    //   this.user = value;
    // });
    // console.log(this.user)
  }


}
