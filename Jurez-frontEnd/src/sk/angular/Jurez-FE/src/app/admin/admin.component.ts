import { Component} from '@angular/core';
import {UserService} from '../user.service';
import {Router} from "@angular/router";
import {User} from "../user";
import {Subject} from "rxjs";
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent {

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();
  items: User[] | undefined;
  item = {} as User;
  constructor(private userService: UserService
  , private router: Router) {
    this.reload();
  }

  reload(){
    this.userService.getAll().subscribe(value => {this.items = value
    this.dtTrigger.next(value);})
  }


  delete(item: User) {
    if (confirm(`Delete ${item.firstName} ?`)) {
      this.userService.delete(item.id)
        .subscribe(() => {
          this.reload();
        })
    }
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

}
