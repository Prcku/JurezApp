import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Rezervation} from "./rezervation";
import {User} from "./user";
import {catchError, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RezervationService {

  constructor(private http: HttpClient) {

  }


  getById(id:number){
    return this.http.get<Rezervation>('/api/rezervation/' + id).pipe(
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

  getfreeRezervations(){
    return this.http.get<Rezervation>('/api/rezervation/free/').pipe(
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

  getRezervationOnThisTime(date: string){
    return this.http.get<Rezervation>('/api/rezervation/time/' +date).pipe(
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

  bookRezervation(date: string, id: number){
    // @ts-ignore
    return this.http.put<Rezervation>('/api/rezervation/time/' + date + '/' + id).pipe(
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

  getHowManyUserIsOnThisRezervation(date: string){
      return this.http.get<Rezervation>('/api/rezervation/free/' + date).pipe(
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
  createRezervation(user: User){
    return this.http.post<Rezervation>('/api/rezervation/free/', user).pipe(
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
      case 409: {
        return `Duplicity: ${error.message}`;
      }
      case 500: {
        return `Internal Server Error: ${error.message}`;
      }
      default: {
        return `Unknown Server Error: ${error.message}`;
      }

    }
  }

  cancelRezervation(date: string, id:number){
    // @ts-ignore
    return this.http.put<void>("/api/rezervation/time/" + date + '/' + id ).pipe(
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
}
