# I Love Animals Backend
The server side project of I Love Animals App is Ktor Server project.

Go to [I Love Animals Android Application](https://github.com/iamsuatzengin/i-love-animals-android)

<blockquote>
  The I Love Animals Android Mobile Application has made it easier and faster to reach and help more stray animals. Users who register in the system can easily find stray animals in need of help near their location and provide health, food support, and adoption services. Additionally, screens have been developed to inform users about First Aid support, which is as important for animals as it is for us. And the application also includes many other features.
</blockquote>

## ⚒️ Tech Stacks
* [Kotlin](https://kotlinlang.org/)
* [Ktor Server](https://ktor.io/docs/server-create-a-new-project.html)
* [Firebase Admin - Push Notification](https://firebase.google.com/docs/cloud-messaging?hl=tr)
* [PostgreSQL](https://www.postgresql.org/)
* [Exposed](https://ktor.io/docs/server-integrate-database.html) for database integration. ORM Framework
* [Authentication](https://ktor.io/docs/server-auth.html)
* [Kotlinx.Serialization](https://github.com/Kotlin/kotlinx.serialization) for serialization/deserialization.

## Endpoints

### Auth
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `POST`   | `/register`                              | User register.                           |
| `POST`   | `/login`                                 | User login.                              |

### User Profile
| Method   | URL                                       | Description                              |
| -------- | ---------------------------------------- | ----------------------------------------  |
| `GET`    | `/user/{userId}`                          | Get user with userId params              |
| `PUT`    | `/user/{userId}`                          | Update user.                             |

### Advertisement
| Method      | URL                                           | Description                                                                         |
| --------    | ----------------------------------------      | ----------------------------------------                                            |
| `GET`       | `/advertisement-list`                         | Get advertisement list.                                                             |
| `GET`       | `/advertisement-list/postalCode/{postalCode}` |  Get advertisement list by postal code. This way user can see nearby advertisements |
| `GET`       | `/advertisement-list/filter`                  | Get filtered advertisement list by category.                                        |
| `POST`      | `/add-advertisement`                          | Add new advertisement.                                                              |
| `GET`       | `/advertisement/{id}`                         | Get advertisement by its id.                                                        |
| `PUT`       | `/advertisement/{id}`                         | Update advertisement.                                                               |
| `DELETE`    | `/advertisement/{id}`                         | Delete advertisement.                                                               |
| `GET`       | `/user-advertisement/{userId}`                | Advertisements that belongs to user given by id.                                    |

### Advertisement comments
| Method      | URL                                                     | Description                                        |
| --------    | ----------------------------------------                | ----------------------------------------           |
| `GET`       | `/advertisement/{id}/comments`                          | Get advertisement comments.                        |
| `POST`      | `/advertisement/{id}/comment`                           | Add a new comment to the advertisement.            |
| `PUT`       | `/comment/{commentId}`                                  | Update the comment.                                |
| `DELETE`    | `/comment/{commentId}`                                  | Delete the comment.                                |

### Search advertisement
| Method      | URL                                                     | Description                                        |
| --------    | ----------------------------------------                | ----------------------------------------           |
| `GET`       | `/search`                                               | Search advertisements by query parameters.         |

### Clinics
| Method    | URL                                       | Description                                             |
| --------  | ----------------------------------------  | ----------------------------------------                |
| `GET`     | `/clinics`                                | Get all clinics                                         |
| `GET`     | `/clinics/{postalCode}`                   | Get clinics by postal code. For nearby clinics.         |
| `POST`    | `/clinics`                                | Add new clinics.                                        |

### Charity score
| Method      | URL                                     | Description                 |
| --------    | ----------------------------------------| ----------------------------|
| `GET`       | `/charity-score`                        | Get charity scores.         |
| `PUT`       | `/charity-score`                        | Update charity scores.      |

### Complete advertisement
| Method    | URL                                       | Description                                                                      |
| --------  | ----------------------------------------  | ----------------------------------------                                         |
| `POST`    | `/complete-advertisement`                 | Complete advertisement. Users earn points when they complete the advertisement.  |

### Push notification device
| Method    | URL                                       | Description                                                                                                   |
| --------  | ----------------------------------------  | ----------------------------------------                                                                      |
| `POST`    | `/push-notification-device/{deviceToken}` | Save the user's registeration token. This token is given from Firebase and we use to send push notification.  |
        
