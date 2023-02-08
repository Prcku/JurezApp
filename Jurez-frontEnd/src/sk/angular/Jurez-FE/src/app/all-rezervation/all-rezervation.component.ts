import { Component} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {DatePipe} from "@angular/common";
import {UserHashMap} from "../userHashMap";

@Component({
  selector: 'app-all-rezervation',
  templateUrl: './all-rezervation.component.html',
  styleUrls: ['./all-rezervation.component.scss']
})
export class AllRezervationComponent {

    jojo: UserHashMap | undefined;
    selectedDate: string | undefined;
    dateTimeNow: string | undefined;
    date = new Date();
    timeoptions: Date[] = [];

  constructor(private userService: UserService) {
    this.reload()
  }

  format = (input: number, padLength: number): string => {
    return input.toString().padStart(padLength, '0');
  };

  reload(){
    if (this.selectedDate == "Zajtra"){
      this.date = new Date(this.date.getTime() + (1000 * 60 * 60 * 24));
    }
    else if (this.selectedDate == "Včera"){
      this.date = new Date(this.date.getTime() - (1000 * 60 * 60 * 24));
    }
    for (let i=0 ; i < 13; i++){
      this.timeoptions[i] = new Date();
      if (this.selectedDate == "Zajtra"){
        this.timeoptions[i] = new Date(this.date.getTime() + (1000 * 60 * 60 * 24));
      }
      else if (this.selectedDate == "Včera"){
        this.timeoptions[i] = new Date(this.date.getTime() - (1000 * 60 * 60 * 24));
      }
      this.timeoptions[i].setHours(6+i, i*15, 0, 0);
    }
    this.date.setHours(6, 59, 0, 0);
    this.dateTimeNow =
      this.format(this.date.getFullYear(), 4) +
      '-' +
      this.format(this.date.getMonth() + 1, 2) +
      '-' +
      this.format(this.date.getDate(), 2) +
      ' ' +
      this.format(this.date.getHours(), 2) +
      ':' +
      this.format(this.date.getMinutes(), 2) +
      ':' +
      this.format(this.date.getSeconds(), 2);

    console.log(this.dateTimeNow)
    this.userService.getAllUsersInRezervationInDay(this.dateTimeNow).subscribe( value => {
      this.jojo = value;
      console.log("tu je to")
      console.log(value)
    })
  }

  options = [
    { name: "Dnes", value: 1 },
    { name: "Včera", value: 2 },
    { name: "Zajtra", value: 3 }
  ]



}
