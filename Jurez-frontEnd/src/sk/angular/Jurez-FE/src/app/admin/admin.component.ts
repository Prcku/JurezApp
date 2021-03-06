import { Component} from '@angular/core';
import {UserService} from '../user.service';
import {Router} from "@angular/router";
import {User} from "../user";
import {Subject} from "rxjs";
import {RezervationService} from "../rezervation.service";
import {Rezervation} from "../rezervation";

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
  constructor(private userService: UserService,
  private rezervationService: RezervationService,
  private router: Router) {
    this.reload();
  }

  reload(){
    this.userService.getAll().subscribe(value => {this.items = value
    this.dtTrigger.next(value);})
    // window.location.reload();

  }

  createRez(input: HTMLInputElement){
    this.rezervationService.createRezervation(input.value).subscribe();

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
