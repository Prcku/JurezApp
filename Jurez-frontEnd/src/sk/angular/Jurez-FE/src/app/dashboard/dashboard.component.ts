import { Component} from '@angular/core';
import {Router} from "@angular/router";
import {RezervationService} from "../rezervation.service";
import {Rezervation} from "../rezervation";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  items: Rezervation[] | undefined;
  item = {} as Rezervation;
  constructor(private rezervationService: RezervationService
    , private router: Router) {
    this.reload();
  }

  reload(){
    this.rezervationService.getfreeRezervations().subscribe(value => {this.items = value})
  }


}
