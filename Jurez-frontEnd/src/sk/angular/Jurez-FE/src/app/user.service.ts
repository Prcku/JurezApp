import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Rezervation} from "./rezervation";
import {User} from "./user";
import {catchError, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {


  // uri = 'http://localhost:8080'
  constructor(private http: HttpClient) {

  }

  getAll(){
    return this.http.get<User[]>('/api/user');
  }

  getById(id:number){
    return this.http.get<User>('/api/user/' + id);
  }

  getUserRezervation(id:number){
    return this.http.get<Rezervation>('/api/user/rezervation/' + id);
  }

  add(user: User){
      return this.http.post('/api/user', user).pipe(
        catchError(error => {
          let errorMsg: string;
          if (error.error instanceof ErrorEvent) {
            errorMsg = `Error: ${error.error.message}`;
          } else {
            errorMsg = this.getServerErrorMessage(error);
          }
          throw new Error(errorMsg);
        })
      );
  }

  private getServerErrorMessage(error: HttpErrorResponse): string {
    switch (error.status) {
      case 400: {
        return `Not Found: ${error.message}`;
      }
      case 403: {
        return `Access Denied: ${error.message}`;
      }
      case 500: {
        return `Internal Server Error: ${error.message}`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }

    }
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
  }

  edit(user: User) {
    return  this.http.put<void>(`/api/user/${user.id}`, user)

  }

  delete(id:number){
    return this.http.delete<User>("/api/user/" + id )
  }
}
