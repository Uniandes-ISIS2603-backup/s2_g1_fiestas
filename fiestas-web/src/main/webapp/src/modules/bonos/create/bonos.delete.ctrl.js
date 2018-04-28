/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function(ng){
    var mod =  ng.module ("BonosModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonoDeleteCtrl', ['$scope', '$http', 'clientesContext', '$state',
        /**
         * @ngdoc controller
         * @name clientes.controller:clienteDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} clientesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, clientesContext, $state) {
            var idBonos = $state.params.bonoId;
            /**
             * @ngdoc function
             * @name deleteCliente
             * @methodOf clientes.controller:clienteDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el autor.
             * @param {String} id El ID del autor a eliminar.
             */        
            $scope.deleteBono = function () {
                $http.delete(clientesContext + '/' + idCliente, {}).then(function (response) {
                    $state.go('clientesList', {clienteId: response.data.id}, {reload: true});
                });
            };
        }
