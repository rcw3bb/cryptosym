# CryptoSym

A lightweight symmetric encryption implementation.

## KeyResolver Interface

Only the implementation of the KeyResolver interface must be used for resolving a key. This is to have consistency of resolving the key.

The default implementations are as follows:

| Implementation Constructor       | Usage                                                        |
| -------------------------------- | ------------------------------------------------------------ |
| EnvAsKey(String keyHolder)       | Use this if the key is being held in an environment variable. Where the `keyHolder` must be the name of the `environment variable`.<br /><br />This is useful if the key is held in a custom environment variable *(i.e. not the CRYPTOSYM_KEY)*. |
| EnvAsKeyByProp(String keyHolder) | Use this if a system property is holding the name of the environment variable holding the key. Where the `keyHolder` must be the `system property name`. <br /><br />This is useful if you don't want to provide the key as a system property *(i.e. not the cryptosym.keyholder)* since the actual key is held in a custom environment variable *(i.e. not the CRYPTOSYM_KEY)*. |
| KeyChain()                       | Use this implementation to find the key using the following sequence:<br />`cryptosym.key property` -> `cryptosym.keyholder property` -> `CRYPTOSYM_KEY` environment variable<br /><br />This mean the traversal stops to the first one who provided the value. Moreover, this is also the implementation being used when there is no KeyResolver implementation provided to either encrypt or decrypt method. |
| NullKeyResolver()                | The null object implementation of the KeyResolver.           |
| PropAsKey(String keyHolder)      | Use this implementation if the key is being held in a system property. Where the keyHolder is the name of the system property.<br /><br />This is useful if you want to provide the key as a system property *(i.e. not the cryptosym.key)*. |
| StringAsKey(String key)          | Use this implementation if the actual key known.             |

## SecretMgr Class

The SecretMgr class is the one that is responsible for generating a key. 

| Method                                | Description                                                  |
| ------------------------------------- | ------------------------------------------------------------ |
| generateKeyAsString() : String        | Generates a key based on AES algorithm of size 256.          |
| generateKeyAsString(String algorithm) | Generates a key based on the the algorithm provided the size will always be 256 in size. |

## CryptoSym Class

The CryptoSym class is the class to use do encryption and decryption.

| Method                                                 | Usage                                                        |
| ------------------------------------------------------ | ------------------------------------------------------------ |
| decrypt(KeyResolver keyResolver, String encryptedText) | Use this if a KeyResolver is required to decrypt an encryptedText. |
| decrypt(String encryptedText)                          | Use this to use the `KeyChain` implementation to decrypt an encryptedText. |
| encrypt(KeyResolver keyResolver, String text)          | Use this if a KeyResolver is required to encrypt a text.     |
| encrypt(String encryptedText)                          | Use this to use the `KeyChain` implementation to encrypt a text. |

## Usage in a project

1. Add the following dependency:

   | Field    | Value              |
   | -------- | ------------------ |
   | Group    | xyz.ronella.crypto |
   | Artifact | cryptosym          |
   | Version  | 1.0.0              |

2. Add the following module in **module-info.java**:

   `requires xyz.ronella.crypto.symmetric;`

## CryptoSym-GUI

CryptoSym-GUI is a UI to aid the generation of key and the testing of encryption and decryption.

![cryptosym-gui](cryptosym-gui.png)

You can build this application by following the [build](BUILD.md) documentation.

## Author

* Ronaldo Webb

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## [Build](BUILD.md)

## [Changelog](CHANGELOG.md)

