document.addEventListener('DOMContentLoaded', function () {
    const menuLinks = document.querySelectorAll('.menu a');
    const pages = document.querySelectorAll('.page');
    const pageTitle = document.getElementById('page-title');

    // --- Mock Data ---
    const scanHistoryData = [
        { id: 'a1b2c3d4', filename: 'document.pdf', status: 'Clean', virus: '-', scannedAt: '2023-10-27 10:30:00' },
        { id: 'e5f6g7h8', filename: 'archive.zip', status: 'Infected', virus: 'Win.Trojan.Generic-123', scannedAt: '2023-10-27 10:28:15' },
        { id: 'i9j0k1l2', filename: 'image.jpg', status: 'Clean', virus: '-', scannedAt: '2023-10-27 10:25:45' },
        { id: 'm3n4o5p6', filename: 'script.js', status: 'Clean', virus: '-', scannedAt: '2023-10-27 10:22:10' },
    ];

    const apiKeysData = [
        { key: 'cg_sk_...', status: 'Active', createdAt: '2023-10-26 14:00:00' },
        { key: 'cg_sk_...', status: 'Revoked', createdAt: '2023-09-15 11:00:00' },
    ];

    // --- Page Navigation ---
    menuLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            // Deactivate all links and pages
            menuLinks.forEach(l => l.parentElement.classList.remove('active'));
            pages.forEach(p => p.classList.remove('active'));

            // Activate clicked link and corresponding page
            this.parentElement.classList.add('active');
            const targetPageId = this.getAttribute('href').substring(1);
            document.getElementById(targetPageId).classList.add('active');

            // Update page title
            pageTitle.textContent = this.textContent;
        });
    });

    // --- Set Initial Page --- 
    // Trigger a click on the first menu link to set the initial state
    if (menuLinks.length > 0) {
        menuLinks[0].click();
    }

    // --- Mock Data for Dashboard Lists ---
    const recentScansData = [
        { filename: 'report-final.docx', status: 'Clean' },
        { filename: 'setup.exe', status: 'Infected' },
        { filename: 'presentation.pptx', status: 'Clean' },
        { filename: 'data.zip', status: 'Clean' },
    ];

    const topThreatsData = [
        { name: 'Win.Trojan.Generic-123', count: 45 },
        { name: 'JS.Adware.Subprop-456', count: 23 },
        { name: 'Linux.Exploit.CVE-2023-1234', count: 12 },
        { name: 'PDF.Phishing.Bank-789', count: 5 },
    ];

    // --- Populate Dashboard Lists ---
    const recentScansList = document.getElementById('recent-scans-list');
    recentScansData.forEach(item => {
        const li = document.createElement('li');
        li.innerHTML = `
            <span class="filename">${item.filename}</span>
            <span class="scan-status-${item.status.toLowerCase()}">${item.status}</span>
        `;
        recentScansList.appendChild(li);
    });

    const topThreatsList = document.getElementById('top-threats-list');
    topThreatsData.forEach(item => {
        const li = document.createElement('li');
        li.innerHTML = `
            <span class="threat-name">${item.name}</span>
            <span class="threat-count">${item.count}</span>
        `;
        topThreatsList.appendChild(li);
    });

    // --- Populate Scan History Table ---
    const tableBody = document.querySelector('#scan-history tbody');
    scanHistoryData.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.id}</td>
            <td>${item.filename}</td>
            <td>${item.status}</td>
            <td>${item.virus}</td>
            <td>${item.scannedAt}</td>
        `;
        tableBody.appendChild(row);
    });

    // --- Populate API Keys Table ---
    const apiKeysTableBody = document.querySelector('#api-keys tbody');
    apiKeysData.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.key}</td>
            <td>${item.status}</td>
            <td>${item.createdAt}</td>
            <td><button class="btn-danger">Revoke</button></td>
        `;
        apiKeysTableBody.appendChild(row);
    });
});