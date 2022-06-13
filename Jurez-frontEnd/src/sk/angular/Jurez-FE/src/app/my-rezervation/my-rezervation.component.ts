import { Component} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Rezervation} from "../rezervation";

@Component({
  selector: 'app-my-rezervation',
  templateUrl: './my-rezervation.component.html',
  styleUrls: ['./my-rezervation.component.scss']
})
export class MyRezervationComponent  {

  item = {} as User;
  items: Rezervation[] | undefined;
  private id: number;

  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private router: Router) {
    this.id = Number(route.snapshot.paramMap.get('id'));
    userService.getById(this.id)
      .subscribe(
        value => {
          console.log(value)
          this.item = value;
          console.log(this.item)
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
    this.userService.getUserRezervation(this.item.id).subscribe(value => {this.items = value})
  }

}
