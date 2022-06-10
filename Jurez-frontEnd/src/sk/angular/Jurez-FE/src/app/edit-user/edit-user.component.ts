import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import {User} from "../user";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent  {

  item = {} as User;
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
      })
  }


  submit(form: NgForm) {
    if(form.invalid || !this.item){
      return
    }

    this.userService.edit(this.item)
      .subscribe(() => {
        this.router.navigate(['/adminpage'])
      })
  }

}
