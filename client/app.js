var app = angular.module("EmployeeManagement", ['ngCookies']);

app.service('Role', function() {
  var service = this,
      roles = [
        {
          "key": "admin",
          "value": "Administrador"
        }, {
          "key": "pm",
          "value": "Gerente de Projetos"
        }, {
          "key": "usr",
          "value": "Usuário"
        }
      ];

      service.getRoles = function() {
        return roles;
      }
});

app.controller("EmployeeController", function($rootScope, $scope, $http, $cookieStore, Role) {
      $scope.equipes = [];
      $scope.roles = Role.getRoles();
      $scope.rolesSelected = {};
      
      $scope.employeeForm = {
        "nome": "",
        "dataNascimento": "",
        "endereco": {
            "rua": "",
            "numero": "",
            "complemento": "",
            "bairro": "",
            "cidade": "",
            "estado": ""
        },
        "dataContratacao": "",
        "foto": "",
        "equipe": {
            "id": "",
            "nome": ""
        },
        "matricula": ""
      };
      
      $scope.login = {
          "username": "",
          "password": ""
      };
      
      $scope.rolesForm = {
          "username": "",
          "role": [],
          "password": ""
      }
      
      $scope.searchEquipes = function() {
        var accessToken = $cookieStore.get("accessToken");
        var tokenType = $cookieStore.get("tokenType");
        $http({
          method: "GET",
          url: "http://localhost:8080/equipe/",
          headers: {
            "Authorization": tokenType + " " + accessToken
          }
        }).then(
          function(res) { // success
            $scope.equipes = res.data;
          },
          function(res) { // error
            _error(res);
          }
        );
      }
      
      $scope.submitEmployee = function() {
          var accessToken = $cookieStore.get("accessToken");
          var tokenType = $cookieStore.get("tokenType");
          $http({
              method: "POST",
              url: "http://localhost:8080/funcionario/",
              data: angular.toJson($scope.employeeForm),
              headers: {
                'Content-Type': 'application/json',
                "Authorization": tokenType + " " + accessToken 
              }
          }).then(
            function(res) { // success
              _clearFormFuncionario();
              alert("Usuário salvo com sucesso!");
            },
            function(res) { // error
              _error(res);
            }
          );
      };
      
      $scope.submitSignup = function() {
        $.each($scope.rolesSelected, function(key, value) {
          if (value = true)
            $scope.rolesForm.role.push(key);
        })
        $http({
          method: "POST",
          url: "http://localhost:8080/api/signup",
          data: angular.toJson($scope.rolesForm),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(
            function(res) { // success
              alert("Usuário registrado com Sucesso!")
              document.location="login.html";
            },
            function(res) { // error
              alert("Usuário já cadastrado!");
            }
          );
      }
      
      $scope.submitLogin = function() {
        $http({
          method: "POST",
          url: "http://localhost:8080/api/signin",
          data: angular.toJson($scope.login),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(
            function(res) { // success
              $cookieStore.put("accessToken", res.data.accessToken);
              $cookieStore.put("tokenType", res.data.tokenType);
              document.location="home.html";
            },
            function(res) { // error
              _error(res);
            }
          );
      };
      
      function _error(res) {
        $cookieStore.remove("accessToken");
        $cookieStore.remove("tokenType");
        if (res.data.error === "Unauthorized") {
                alert("Sessão expirada");
                document.location="login.html";
        } else if (res.data.error === "Forbidden") {
            alert("Você não tem previlégios para executar essa operação.");
        } 
        console.log("Error: " + res.status + " : " + res.data);
      }
      
      // Clear the form
      function _clearFormFuncionario() {
          $scope.employeeForm.nome = "";
          $scope.employeeForm.dataNascimento = "";
          $scope.employeeForm.endereco.rua = "";
          $scope.employeeForm.endereco.numero = "";
          $scope.employeeForm.endereco.complemento = "";
          $scope.employeeForm.endereco.bairro = "";
          $scope.employeeForm.endereco.cidade = "";
          $scope.employeeForm.endereco.estado = "";
          $scope.employeeForm.dataContratacao = "";
          $scope.employeeForm.foto = "";
          $scope.employeeForm.matricula = "";
          $scope.employeeForm.equipe.nome = "";
      };
});
