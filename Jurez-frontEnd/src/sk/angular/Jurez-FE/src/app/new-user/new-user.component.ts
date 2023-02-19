import {Component, TemplateRef} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";


@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
})
export class NewUserComponent  {
  item = {} as User;

  constructor(private users: UserService,
              private router: Router,
                ) {

  }


  submit(formElemnt: NgForm){
    if (formElemnt.invalid){
      return
    }


    this.users.add(this.item).subscribe(
      () => (console.log("ahoj")),
      error => {
        if (error.message == "Duplicity"){
        console.log("vypis na obrazovku zadal si rovanky mail zmen ho")
      }},
      () => (this.router.navigate(['/login']))
    )
  }
}
