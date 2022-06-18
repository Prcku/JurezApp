import { Component} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Rezervation} from "../rezervation";
import {RezervationService} from "../rezervation.service";

@Component({
  selector: 'app-my-rezervation',
  templateUrl: './my-rezervation.component.html',
  styleUrls: ['./my-rezervation.component.scss']
})
export class MyRezervationComponent  {

  user = {} as User;
  items: Rezervation[] | undefined;
  private id: number;

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private rezervationService: RezervationService,
              private router: Router) {
    this.id = Number(route.snapshot.paramMap.get('id'));
    userService.getById(this.id)
      .subscribe(
        value => {
          this.user = value;
          this.reload();
        })
  }
  // items: User[] | undefined;
  // item = {} as User;
  // constructor(private userService: UserService
  //   , private router: Router) {
  //   this.reload();
  // }

  reload(){
    this.userService.getUserRezervation(this.user.id).subscribe(value => {this.items = value})
  }

  delete(rezervation: Rezervation) {
    if (confirm(`Chcete zrušiť túto rezerváciu ${rezervation.currentTime} ?`)) {
      this.rezervationService.cancelRezervation(rezervation.currentTime, this.id).subscribe(() =>{
          this.reload();
      })
    }
  }

}
