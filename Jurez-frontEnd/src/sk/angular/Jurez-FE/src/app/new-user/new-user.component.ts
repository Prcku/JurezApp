import { Component} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html'
  // styleUrls: ['./new-user.component.scss']
})
export class NewUserComponent  {

  item = {} as User;

  constructor(private users: UserService,
              private router: Router) {

  }
  submit(formElemnt: NgForm){
    if (formElemnt.invalid){
      return
    }

    this.users.add(this.item).subscribe(
      $data => (this.router.navigate(['/adminpage'])),
      error => (console.log(error.message()))

    )
  }
}
