(function() {
    'use strict';
    angular
        .module('cavmgrApp')
        .factory('EmployeeRoles', EmployeeRoles);

    EmployeeRoles.$inject = ['$resource', 'DateUtils'];

    function EmployeeRoles ($resource, DateUtils) {
        var resourceUrl =  'api/employee-roles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.lastUpdateDate = DateUtils.convertDateTimeFromServer(data.lastUpdateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
